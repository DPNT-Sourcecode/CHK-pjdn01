package befaster.solutions.CHK;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static befaster.solutions.CHK.CheckoutCalculator.calculateTotalCost;

public class CheckoutSolution {

    public static void main(String[] args) throws IOException {
        Integer check = checkout("FF");
        System.out.println(check);
    }

    public static Integer checkout(String skus) {
        if (skus == null || skus.isEmpty()) {
            return 0;
        }

        String filename = "D:\\akahuc\\Documents\\runner-for-java-windows\\accelerate_runner\\src\\main\\resources\\itemCatalogue.json";

        try {
            //        Gson gson = new Gson();
//        Map<Object, Object> myObj =gson.fromJson()

            ObjectMapper mapper = new ObjectMapper();
            Catalogue myObj = mapper.readValue(new FileReader(filename), Catalogue.class);
//            ItemPrice itemPrice = myObj.get(ItemType.A);
            Map<ItemType, Integer> itemToCountMap = getItemToCountMap(skus);

            Integer totalCost = calculateTotalCost(itemToCountMap);
            return totalCost;

        } catch (Exception e) {
            return -1;
        }

    }

    private static Map<ItemType, Integer> getItemToCountMap(String skus) {
        Map<ItemType, Integer> itemToCountMap = new HashMap<>();
        for (String str : skus.split("")) {
            itemToCountMap.merge(ItemType.forName(str), 1, Integer::sum);
        }
        return itemToCountMap;
    }
}
