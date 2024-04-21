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
            ItemPrice value = (ItemPrice) item.getValue();
        }
        return newData;
    }
}
