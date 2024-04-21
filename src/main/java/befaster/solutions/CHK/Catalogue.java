package befaster.solutions.CHK;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Catalogue implements Serializable {
    Map<ItemType, ItemPrice> catalogue;

    public static Map<ItemType, ItemPrice> buildCatalogue(Map<Object, Object> data) {
        Map<ItemType, ItemPrice> newData = new HashMap<>();
        for (Map.Entry<Object, Object> item : data.entrySet()) {
            ItemType keyStr = ItemType.forName((String) item.getKey());
            Map<String, Object> value = (Map<String, Object>) item.getValue();
            System.out.println(value);

//            ItemPrice value = (ItemPrice) item.getValue();
        }
        return newData;
    }
}

