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
            return simpleCheckoutCal(itemToCountMap);
        }
        return complexCheckoutCal(itemToCountMap);
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
        Map<ItemType, Integer> itemCheckoutPrice = new HashMap<>();
        for (Map.Entry<String, Integer> item : itemToCountMap.entrySet()) {
            itemCheckoutPrice.put(ItemType.forName(item.getKey()), calculateBestOfferPrice(item));
        }
        return -1;
    }

    private static Integer calculateBestOfferPrice(Map.Entry<String, Integer> item) {
        return calculateBestOfferPrice(item, 0);
    }

    private static Integer calculateBestOfferPrice(Map.Entry<String, Integer> item, int numberOfFreebies) {
        Set<Integer> prices = new HashSet<>();
        ItemType itemType = ItemType.forName(item.getKey());
        ItemPrice itemPrice = ItemToPriceMap2.get(itemType);
        int quantity = item.getValue() - numberOfFreebies;
        if (itemPrice.getSpecialOffers().isPresent()) {
            List<Offer> offers = itemPrice.getSpecialOffers().get().getOffers();
            long offerItemTypes = offers.stream().map(Offer::getItemType).distinct().count();
            if (offerItemTypes == 1 && offers.stream().iterator().next().getItemType() == itemType) {
                offers.forEach(offer -> {
                    int offerQuantityUnit = item.getValue() / quantity;
                    int remainingQuantity = item.getValue() - (offerQuantityUnit * quantity);
                    int totalCost = (offerQuantityUnit * offer.getUnitPrice()) + (remainingQuantity * itemPrice.getUnitPrice());
                    prices.add(totalCost);
                });
            } else if (offerItemTypes == 1 && offers.stream().iterator().next().getItemType() != itemType) {
                offers.forEach(offer -> {
                    int offerPrice = offer.getUnitPrice();
                    int offerQuantityUnit = item.getValue() / quantity;
                    if (offerPrice == 0) {
                        int totalCost = (item.getValue() * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    } else {
                        int remainingQuantity = item.getValue() - (offerQuantityUnit * quantity);
                        int totalCost = (offerQuantityUnit * offer.getUnitPrice()) + (remainingQuantity * itemPrice.getUnitPrice());
                        prices.add(totalCost);
                    }
                });
            }
        }
        int totalCost = (item.getValue() * itemPrice.getUnitPrice());
        prices.add(totalCost);

        return Collections.min(prices);
    }

    private static void applyFreebiesOnItem(Map.Entry<String, Integer> item, int quantity) {

    }

    private static void updateCheckoutPrice(Map<ItemType, List<Integer>> itemCheckoutPrice, ItemType itemType, int totalCost) {
        List<Integer> checkoutPrices = itemCheckoutPrice.get(itemType);
        if (checkoutPrices == null) {
            itemCheckoutPrice.put(itemType, List.of(totalCost));
        } else {
            checkoutPrices.add(totalCost);
            itemCheckoutPrice.put(itemType, checkoutPrices);
        }
    }

}


