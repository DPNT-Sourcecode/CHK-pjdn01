package befaster.solutions.CHK;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static befaster.solutions.CHK.CheckoutCalculator.calculateTotalCost;

public class CheckoutSolution {

    public static void main(String[] args) {
        Integer check = checkout("FF");
        System.out.println(check);
    }
    public static Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return 0;
        }
        Gson gson = new Gson();
        Map<Object, Object> myObj =gson.fromJson()

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


