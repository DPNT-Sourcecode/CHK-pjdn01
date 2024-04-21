package befaster.solutions.CHK;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static befaster.solutions.CHK.Catalogue.buildCatalogue;
import static befaster.solutions.CHK.CheckoutCalculator.calculateTotalCost;

public class CheckoutSolution {

    private static final String filename = "D:\\akahuc\\Documents\\runner-for-java-windows\\accelerate_runner\\src\\main\\resources\\itemCatalogue.json";

    private static final Map<ItemType, ItemPrice> catalogue = new HashMap<>();

    public CheckoutSolution() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<Object, Object> data = mapper.readValue(new FileReader(filename), Map.class);
            catalogue.putAll(buildCatalogue(data));
        } catch (Exception e) {
            System.out.println("Error occurred loading catalogue data");
            System.exit(-1);
        }

    }

    public static void main(String[] args) {
        Integer check = checkout("FF");
        System.out.println(check);
    }

    public static Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return 0;
        }

        Map<ItemType, Integer> itemToCountMap = getItemToCountMap(skus);

        Integer totalCost = calculateTotalCost(itemToCountMap, catalogue);
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

