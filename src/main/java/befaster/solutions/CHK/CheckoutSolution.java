package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap;

public class CheckoutSolution {
    public static Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return 0;
        }
        Map<String, Integer> itemToCountMap = getItemToCountMap(skus);

        return computeTotalCost(itemToCountMap);
    }

    private static Map<String, Integer> getItemToCountMap(String skus) {
        Map<String, Integer> itemToCountMap = new HashMap<>();
        for (String str : skus.split("")) {
            itemToCountMap.merge(str, 1, Integer::sum);
        }
        return itemToCountMap;
    }

    private static Integer computeTotalCost(Map<String, Integer> itemToCountMap) {
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
                int remainingQuantity = item.getValue() - specialQuantityUnit;
                totalCost += (specialQuantityUnit * specialOfferPrice.get()) + (remainingQuantity * unitPrice);
            } else {
                totalCost += (item.getValue() * unitPrice);
            }
        }
        return totalCost;
    }
}
