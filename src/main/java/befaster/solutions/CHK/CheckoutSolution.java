package befaster.solutions.CHK;

import befaster.solutions.CHK.model.*;
import befaster.solutions.CHK.model.enums.ItemType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.util.Map;

import static befaster.solutions.CHK.model.Catalogue.*;
import static befaster.solutions.CHK.CheckoutCalculator.calculateTotalCost;
import static befaster.solutions.CHK.model.CheckoutUtils.*;

public class CheckoutSolution {


    public static void main(String[] args) {
        Integer check = checkout("S");
        System.out.println(check);
    }

    public static Integer checkout(String skus) {
        try {
            if (skus == null || skus.isEmpty()) {
                return 0;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<Object, Object> data = mapper.readValue(new FileReader(filename), Map.class);
            Map<ItemType, ItemPrice> catalogue = buildCatalogue(data);
            Map<ItemType, Integer> itemToCountMap = getItemToCountMap(skus, catalogue, false);
            Map<ItemType, Group> groupMap = buildGroup(skus, catalogue);
            return calculateTotalCost(itemToCountMap, groupMap, catalogue);
        } catch (Exception e) {
            System.out.println("Error occurred loading catalogue data");
            return -1;
        }
    }
}



