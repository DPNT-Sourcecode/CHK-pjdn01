package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static befaster.solutions.CHK.CheckoutCalculator.calculateTotalCost;
import static befaster.solutions.CHK.CheckoutUtils.ItemToPriceMap;

public class CheckoutSolution {

    public static void main(String[] args) {
        Integer check = checkout("AAAAAAAA");
        System.out.println(check);
    }
    public static Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return 0;
        }
        Map<ItemType, Integer> itemToCountMap = getItemToCountMap(skus);

        Integer totalCost = calculateTotalCost(itemToCountMap);
        return totalCost;
    }

    private static Map<ItemType, Integer> getItemToCountMap(String skus) {
        Map<ItemType, Integer> itemToCountMap = new HashMap<>();
        for (String str : skus.split("")) {
            itemToCountMap.merge(ItemType.forName(str), 1, Integer::sum);
        }
        return itemToCountMap;
    }
}

