package befaster.solutions.CHK;

import java.util.*;

import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap;
import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap2;

public class CheckoutCalculator {
    public static Integer calculateTotalCost(Map<String, Integer> itemToCountMap) {

        Optional<ItemType> hasUnknownItem = itemToCountMap.keySet().stream()
                .map(ItemType::forName)
                .filter(type -> type == ItemType.UNKNOWN)
                .map(Optional::of)
                .findFirst()
                .orElse(Optional.empty());

        if (hasUnknownItem.isPresent()) {
            return -1;
        }

        Optional<Optional<SpecialOffers>> hasSpecialOffers = itemToCountMap.keySet().stream()
                .map(ItemType::forName)
                .filter(type -> type != ItemType.UNKNOWN)
                .map(ItemToPriceMap2::get)
                .map(ItemPrice::getSpecialOffers)
                .filter(Objects::nonNull)
                .map(Optional::of)
                .findFirst()
                .orElse(Optional.empty());

        if (hasSpecialOffers.isEmpty()) {

        }

        int totalCost = 0;
        for (Map.Entry<String, Integer> item : itemToCountMap.entrySet()) {
            ItemPrice itemPrice = ItemToPriceMap.get(ItemType.forName(item.getKey()));
            if (itemPrice == null) {
                return -1;
            }
            final Integer unitPrice = itemPrice.getUnitPrice();

            if (unitPrice == null) {
                return -1;
            }

            final Optional<Integer> specialOfferPrice = itemPrice.getSpecialOfferPrice();
            final Optional<Integer> specialOfferQuantity = itemPrice.getSpecialOfferQuantity();
            if (specialOfferPrice.isPresent() && specialOfferQuantity.isPresent()) {
                int specialQuantityUnit = item.getValue() / specialOfferQuantity.get();
                int remainingQuantity = item.getValue() - (specialQuantityUnit * specialOfferQuantity.get());
                totalCost += (specialQuantityUnit * specialOfferPrice.get()) + (remainingQuantity * unitPrice);
            } else {
                totalCost += (item.getValue() * unitPrice);
            }
        }
        return totalCost;
    }

    private static int simpleCheckoutCal(Map<String, Integer> itemToCountMap) {
        int totalCost = 0;
        for (Map.Entry<String, Integer> item : itemToCountMap.entrySet()) {
            ItemPrice itemPrice = ItemToPriceMap.get(ItemType.forName(item.getKey()));
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

    private static int complexCheckoutCal(Map<String, Integer> itemToCountMap) {
        // EEBB
        int totalCost = 0;
        for (Map.Entry<String, Integer> item : itemToCountMap.entrySet()) {
            ItemPrice itemPrice = ItemToPriceMap2.get(ItemType.forName(item.getKey()));
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

    private static int options(Map<String, Integer> itemToCountMap) {
        // EEBB
        List<Integer> avaliableOptions = new ArrayList<>();
        Map<ItemType, List<Integer>> itemCheckoutPrice = new HashMap<>();
        for (Map.Entry<String, Integer> item : itemToCountMap.entrySet()) {
            ItemType itemType = ItemType.forName(item.getKey());
            ItemPrice itemPrice = ItemToPriceMap2.get(itemType);
            if (itemPrice.getSpecialOffers().isPresent()) {
                List<Offer> offers = itemPrice.getSpecialOffers().get().getOffers();
                long offerItemTypes = offers.stream().map(Offer::getItemType).distinct().count();
                if (offerItemTypes == 1 && offers.stream().iterator().next().getItemType() == itemType) {
                    offers.forEach(offer -> {
                        int offerQuantityUnit = item.getValue() / offer.getQuantity();
                        int remainingQuantity = item.getValue() - (offerQuantityUnit * offer.getQuantity());
                        Integer totalCost = (offerQuantityUnit * offer.getUnitPrice()) + (remainingQuantity * itemPrice.getUnitPrice());
                        List<Integer> merge = itemCheckoutPrice.merge(itemType, new ArrayList<> {totalCost},)
//                        int specialQuantityUnit = item.getValue() / specialOfferQuantity.get();
//                        int remainingQuantity = item.getValue() - (specialQuantityUnit * specialOfferQuantity.get());
//                        totalCost += (specialQuantityUnit * specialOfferPrice.get()) + (remainingQuantity * unitPrice);
                    });
                }
            }
        }
        return -1;
    }

    private List<Integer> updateCheckoutPrice(List<Integer> checkoutPrice, int newPrice) {
        checkoutPrice.add(newPrice);
        return checkoutPrice;
    }

}

