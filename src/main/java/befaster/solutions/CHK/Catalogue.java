package befaster.solutions.CHK;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalogue implements Serializable {
    Map<ItemType, ItemPrice> catalogue;

    public static Map<ItemType, ItemPrice> buildCatalogue(Map<Object, Object> data) {
        Map<ItemType, ItemPrice> newData = new HashMap<>();
        for (Map.Entry<Object, Object> item : data.entrySet()) {
            ItemType keyStr = ItemType.forName((String) item.getKey());
            Map<String, Object> value = (Map<String, Object>) item.getValue();
            Map<String, Object> specialOffers = ((Map<String, Object>) value.get("specialOffers"));
            if (specialOffers != null) {
                List<Object> offers = (List<Object>) specialOffers.get("offers");
                for (Object offer : offers) {
                    Map<String, Object> offerMap = ((Map<String, Object>) offer);
                    System.out.println(value);
                }
                System.out.println(value);
            }

            System.out.println(value);

//            ItemPrice value = (ItemPrice) item.getValue();
        }
        return newData;
    }
}




