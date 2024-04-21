package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return -1;
        }
        Map<String, Integer> itemToCountMap = getItemToCountMap(skus);

        return null;
    }

    private Map<String, Integer> getItemToCountMap(String skus) {
        Map<String, Integer> itemToCountMap = new HashMap<>();
        for (String str : skus.split("")) {
            itemToCountMap.merge(str, 1, Integer::sum);
        }
        return itemToCountMap;
    }

    private Double computeTotalCost(Map<String, Integer> itemToCountMap) {
        Double totalCost = 0d;
        for (Map.Entry<String, Integer> item : itemToCountMap.entrySet()) {
            ItemPrice itemPrice = ItemToPriceMap.get(ItemType.forName(item.getKey()));
            if (itemPrice == null) {
                return -1d;
            }
            final Integer unitPrice = itemPrice.getUnitPrice();
            final Optional<Integer> specialOfferQuantity = itemPrice.getSpecialOfferQuantity();
            final Optional<Integer> specialOfferPrice = itemPrice.getSpecialOfferPrice();
            if (specialOfferQuantity.isPresent() && specialOfferPrice.isPresent()) {

                int specialQuantityUnit = Math.floorMod(item.getValue(), specialOfferQuantity.get());
                int remainingUnits = specialOfferQuantity.get() - specialQuantityUnit;
                totalCost += (specialQuantityUnit * specialOfferPrice)
            }
        }
    }

}




