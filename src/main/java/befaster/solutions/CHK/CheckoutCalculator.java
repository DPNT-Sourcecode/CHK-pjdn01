package befaster.solutions.CHK;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap;
import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap2;

public class CheckoutCalculator {
    public static Integer calculateTotalCost(Map<ItemType, Integer> itemToCountMap) {

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
                .map(ItemToPriceMap2::get)
                .map(ItemPrice::getSpecialOffers)
                .filter(Objects::nonNull)
                .map(Optional::of)
                .findFirst()
                .orElse(Optional.empty());

        if (hasSpecialOffers.isEmpty()) {
            return simpleCheckoutCal(itemToCountMap);
        }
        return complexCheckoutCal(itemToCountMap);
    }

    private static int simpleCheckoutCal(Map<ItemType, Integer> itemToCountMap) {
        int totalCost = 0;
        for (Map.Entry<ItemType, Integer> item : itemToCountMap.entrySet()) {
            ItemPrice itemPrice = ItemToPriceMap.get(item.getKey());
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

    private static int complexCheckoutCal(Map<ItemType, Integer> itemToCountMap) {
        // EEBB
        Map<ItemType, ItemCheckoutPrice> itemCheckoutPrice = new HashMap<>();
        for (Map.Entry<ItemType, Integer> item : itemToCountMap.entrySet()) {
            itemCheckoutPrice.put(item.getKey(), calculateBestOfferPrice(item));
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
                    itemCheckoutPriceWithFreebies.put(freebie.getItemType(), calculateBestOfferPrice(freebieItem, freebie.getNumberOfItem()));
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

    private static ItemCheckoutPrice calculateBestOfferPrice(Map.Entry<ItemType, Integer> item) {
        return calculateBestOfferPrice(item, 0);
    }

    private static ItemCheckoutPrice calculateBestOfferPrice(Map.Entry<ItemType, Integer> item, int numberOfFreebies) {
        Set<Integer> prices = new HashSet<>();
        AtomicReference<Freebies> freebies = new AtomicReference<>();
        ItemType itemType = item.getKey();
        ItemPrice itemPrice = ItemToPriceMap2.get(itemType);
        int numberOfItems = item.getValue() - numberOfFreebies;
        if (itemPrice.getSpecialOffers().isPresent()) {
            List<Offer> offers = itemPrice.getSpecialOffers().get().getOffers();
            long offerItemTypes = offers.stream().map(Offer::getItemType).distinct().count();

            if (offerItemTypes == 1 && offers.stream().iterator().next().getItemType() == itemType) {
                int totalOfferQuantity = offers.stream().map(Offer::getQuantity).reduce(Integer::sum).orElse(0);

                if (totalOfferQuantity > 0 && totalOfferQuantity <= numberOfItems) {
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

            } else if (offerItemTypes == 1 && offers.stream().iterator().next().getItemType() != itemType) {
                offers.forEach(offer -> {
                    int offerQuantityUnit = numberOfItems / offer.getQuantity();
                    if (offer.getUnitPrice() == 0) {
                        int totalCost = (item.getValue() * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    } else {
                        int remainingQuantity = numberOfItems - (offerQuantityUnit * offer.getQuantity());
                        int totalCost = (offerQuantityUnit * offer.getUnitPrice()) + (remainingQuantity * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    }
                    if (offer.getFreebieUnit() > 0 && numberOfItems >= offer.getQuantity()) {
                        freebies.set(new Freebies(offer.getItemType(), offer.getFreebieUnit() * offerQuantityUnit));
                    }
                });
            }
        }
        int totalCost = (numberOfItems * itemPrice.getUnitPrice());
        prices.add(totalCost);

        Integer minPrice = Collections.min(prices);

        return new ItemCheckoutPrice(minPrice, freebies.get());
    }



}
