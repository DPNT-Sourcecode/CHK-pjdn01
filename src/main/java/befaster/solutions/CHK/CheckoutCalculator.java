package befaster.solutions.CHK;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static befaster.solutions.CHK.Catalogue.GROUP_DISCOUNT_MAP;

public class CheckoutCalculator {
    public static Integer calculateTotalCost(
            Map<ItemType, Integer> itemToCountMap,
            Map<ItemType, Group> groupMap,
            Map<ItemType, ItemPrice> catalogue) {

        int totalNonGroupItemsCost = 0;
        int totalGroupItemsCost = 0;
        if (!itemToCountMap.isEmpty()) {
            Optional<ItemType> hasUnknownItem = itemToCountMap.keySet().stream()
                    .filter(type -> type == ItemType.UNKNOWN)
                    .map(Optional::of)
                    .findFirst()
                    .orElse(Optional.empty());

            if (hasUnknownItem.isPresent()) {
                return -1;
            }

            Optional<Optional<SpecialOffers>> hasSpecialOffers = itemToCountMap.keySet().stream()
                    .filter(type -> type != ItemType.UNKNOWN)
                    .map(catalogue::get)
                    .map(ItemPrice::getSpecialOffers)
                    .filter(Objects::nonNull)
                    .map(Optional::of)
                    .findFirst()
                    .orElse(Optional.empty());

            if (hasSpecialOffers.isEmpty()) {
                totalNonGroupItemsCost = simpleCheckoutCal(itemToCountMap, catalogue);
            } else {
                totalNonGroupItemsCost = complexCheckoutCal(itemToCountMap, catalogue);
            }
        }

        if (!groupMap.isEmpty()) {
            totalGroupItemsCost = calculateBestGroupPrice(catalogue, groupMap);
        }
        return totalNonGroupItemsCost + totalGroupItemsCost;
    }

    private static int simpleCheckoutCal(Map<ItemType, Integer> itemToCountMap, Map<ItemType, ItemPrice> catalogue) {
        int totalCost = 0;
        for (Map.Entry<ItemType, Integer> item : itemToCountMap.entrySet()) {
            ItemPrice itemPrice = catalogue.get(item.getKey());
            if (itemPrice == null) {
                return -1;
            }
            final Integer unitPrice = itemPrice.getUnitPrice();

            if (unitPrice == null) {
                return -1;
            }
            totalCost += (item.getValue() * unitPrice);
        }
        return totalCost;
    }

    private static int complexCheckoutCal(Map<ItemType, Integer> itemToCountMap, Map<ItemType, ItemPrice> catalogue) {
        // EEBB
        Map<ItemType, ItemCheckoutPrice> itemCheckoutPrice = new HashMap<>();
        for (Map.Entry<ItemType, Integer> item : itemToCountMap.entrySet()) {
            itemCheckoutPrice.put(item.getKey(), calculateBestOfferPrice(catalogue, item));
        }
        Optional<Freebies> hasFreebies = itemCheckoutPrice.values().stream()
                .map(ItemCheckoutPrice::getFreebies)
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());

        if (hasFreebies.isEmpty()) {
            return getSum(itemCheckoutPrice);
        } else {
            Map<ItemType, ItemCheckoutPrice> itemCheckoutPriceWithFreebies = new HashMap<>();
            for (Map.Entry<ItemType, ItemCheckoutPrice> item : itemCheckoutPrice.entrySet()) {
                Optional<Freebies> optFreebie = item.getValue().getFreebies();
                if (optFreebie.isPresent() && itemToCountMap.get(optFreebie.get().getItemType()) != null) {
                    Freebies freebie = optFreebie.get();
                    Map.Entry<ItemType, Integer> freebieItem = Map.entry(freebie.getItemType(), itemToCountMap.get(freebie.getItemType()));
                    itemCheckoutPriceWithFreebies.put(freebie.getItemType(), calculateBestOfferPrice(catalogue, freebieItem, freebie.getNumberOfItem()));
                }
            }
            if (!itemCheckoutPriceWithFreebies.isEmpty()) {
                for (Map.Entry<ItemType, ItemCheckoutPrice> item : itemCheckoutPriceWithFreebies.entrySet()) {
                    ItemType itemType = item.getKey();
                    int currentPrice = itemCheckoutPrice.get(itemType).getPrice();
                    int freebiePrice = item.getValue().getPrice();
                    Integer minimumPrice = Math.min(currentPrice, freebiePrice);
                    itemCheckoutPrice.put(itemType, new ItemCheckoutPrice(minimumPrice));
                }
            }
            return getSum(itemCheckoutPrice);
        }
    }


    private static Integer getSum(Map<ItemType, ItemCheckoutPrice> itemCheckoutPrice) {
        return itemCheckoutPrice.values().stream()
                .map(ItemCheckoutPrice::getPrice)
                .reduce(Integer::sum).orElse(-1);
    }

    private static ItemCheckoutPrice calculateBestOfferPrice(
            Map<ItemType, ItemPrice> catalogue, Map.Entry<ItemType, Integer> item) {
        return calculateBestOfferPrice(catalogue, item, 0);
    }

    private static ItemCheckoutPrice calculateBestOfferPrice(
            Map<ItemType, ItemPrice> catalogue, Map.Entry<ItemType, Integer> item, int numberOfFreebies) {
        Set<Integer> prices = new HashSet<>();
        AtomicReference<Freebies> freebies = new AtomicReference<>();
        ItemType itemType = item.getKey();
        ItemPrice itemPrice = catalogue.get(itemType);
        int numberOfItems = item.getValue() - numberOfFreebies;
        if (itemPrice.getSpecialOffers().isPresent()) {
            List<Offer> offers = itemPrice.getSpecialOffers().get().getOffers();
            OfferType offerType = offers.stream().iterator().next().getOfferType();

            if (offerType == OfferType.DISCOUNT) {

                List<Integer> offerQuantities = offers.stream().map(Offer::getQuantity).toList();
                int maxOfferQuantity = Collections.max(offerQuantities);
                int totalOfferQuantity = offerQuantities.stream().reduce(Integer::sum).orElseThrow();

                if (maxOfferQuantity > 0 && numberOfItems % maxOfferQuantity == 0) {
                    int offerQuantityUnit = numberOfItems / maxOfferQuantity;
                    Offer offer = offers.stream().filter(o -> o.getQuantity() == maxOfferQuantity).findFirst().orElseThrow();
                    prices.add(offerQuantityUnit * offer.getUnitPrice());
                } else if (totalOfferQuantity > 0 && totalOfferQuantity <= numberOfItems) {
                    int offerQuantityUnit = numberOfItems / totalOfferQuantity;
                    AtomicInteger totalCost = new AtomicInteger();
                    offers.forEach(offer -> totalCost.addAndGet(offerQuantityUnit * offer.getUnitPrice()));
                    int remainingNumberOfItems = numberOfItems - (totalOfferQuantity * offerQuantityUnit);
                    totalCost.addAndGet(remainingNumberOfItems * itemPrice.getUnitPrice());
                    prices.add(totalCost.get());
                } else {
                    offers.forEach(offer -> {
                        int offerQuantityUnit = numberOfItems / offer.getQuantity();
                        int remainingNumberOfItems = numberOfItems - (offerQuantityUnit * offer.getQuantity());
                        int totalCost = (offerQuantityUnit * offer.getUnitPrice()) + (remainingNumberOfItems * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    });
                }
            } else if (offerType == OfferType.FREEBIES) {

                offers.forEach(offer -> {
                    ItemType freebieItemType = offers.stream().iterator().next().getItemType();
                    int offerQuantityUnit = numberOfItems / offer.getQuantity();

                    if (freebieItemType != itemType) {
                        int totalCost = (numberOfItems * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    } else {
                        int remainingQuantity = numberOfItems < offer.getFrequency() ?
                                numberOfItems : numberOfItems - (numberOfItems / offer.getFrequency());
                        int totalCost = (remainingQuantity * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    }

                    if (offer.getFrequency() == null && numberOfItems >= offer.getQuantity()) {
                        freebies.set(new Freebies(offer.getItemType(), offerQuantityUnit));
                    }
                });
            } else if (offerType == OfferType.GROUP_DISCOUNT) {
                GroupDiscount groupDiscount = GROUP_DISCOUNT_MAP.get(
                        offers.stream().iterator().next().getGroupDiscountName()
                );
                int discountQuantity = groupDiscount.getGroupQuantity();
                int discountPrice = groupDiscount.getUnitPrice();
            }
        }
// STXS
        int totalCost = (numberOfItems * itemPrice.getUnitPrice());
        prices.add(totalCost);

        Integer minPrice = Collections.min(prices);

        return new ItemCheckoutPrice(minPrice, freebies.get());
    }

    private static int calculateBestGroupPrice(
            Map<ItemType, ItemPrice> catalogue, Map<ItemType, Group> groupMap) {
        Set<Integer> prices = new HashSet<>();


        for (Map.Entry<ItemType, Group> entry : groupMap.entrySet()) {
            Map<ItemType, Integer> itemToUnitPriceMap = new HashMap<>();
            Map<ItemType, Integer> itemToQuantityMap = new HashMap<>();
            Group group = entry.getValue();
            for (Map.Entry<ItemType, GroupMember> members : group.getMembers().entrySet()) {
                ItemType itemType = members.getKey();
                GroupMember member = members.getValue();
                itemToUnitPriceMap.put(itemType, member.getItemPrice().getUnitPrice());
                itemToQuantityMap.put(itemType, member.getQuantity());
            }
            int numberOfItems = itemToQuantityMap.values().stream()
                    .reduce(Integer::sum).orElseThrow();
            int quantityUnit = numberOfItems / group.getQuantity();
            int remainingQuantity = numberOfItems - (quantityUnit * group.getQuantity());
            int groupTotalCost = (quantityUnit * group.getUnitPrice());
//            prices.add(groupTotalCost);
            for (Map.Entry<ItemType, Integer> unitMap : itemToUnitPriceMap.entrySet()) {
                
            }
            System.out.println(remainingQuantity);

//            group.getValue().getMembers().entrySet();
//                    .stream()
//                    .map(entryMap -> {
//                        ItemType itemType = entryMap.getKey();
//                        entryMap.getValue()
//                    })
        }
        AtomicReference<Freebies> freebies = new AtomicReference<>();
//        ItemType itemType = item.getKey();
//        ItemPrice itemPrice = catalogue.get(itemType);
//        int numberOfItems = item.getValue() - numberOfFreebies;
//        if (itemPrice.getSpecialOffers().isPresent()) {
//            List<Offer> offers = itemPrice.getSpecialOffers().get().getOffers();
//            OfferType offerType = offers.stream().iterator().next().getOfferType();
//
//
//            GroupDiscount groupDiscount = GROUP_DISCOUNT_MAP.get(
//                    offers.stream().iterator().next().getGroupDiscountName()
//            );
//            int discountQuantity = groupDiscount.getGroupQuantity();
//            int discountPrice = groupDiscount.getUnitPrice();
//        }
//// STXS
//        int totalCost = (numberOfItems * itemPrice.getUnitPrice());
//        prices.add(totalCost);
//
//        Integer minPrice = Collections.min(prices);

//        return new ItemCheckoutPrice(minPrice, freebies.get());
        return 0;
    }

}


