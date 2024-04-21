package befaster.solutions.CHK;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalogue implements Serializable {
    public static Map<ItemType, ItemPrice> buildCatalogue(Map<Object, Object> data) {
        Map<ItemType, ItemPrice> newData = new HashMap<>();
        for (Map.Entry<Object, Object> item : data.entrySet()) {
            ItemType itemType = ItemType.forName((String) item.getKey());
            Map<String, Object> value = (Map<String, Object>) item.getValue();
            Integer unitPrice = (Integer) value.get("unitPrice");
            Map<String, Object> specialOffers = ((Map<String, Object>) value.get("specialOffers"));
            if (specialOffers != null) {
                List<Object> offers = (List<Object>) specialOffers.get("offers");
                for (Object offer : offers) {
                    Map<String, Object> offerMap = ((Map<String, Object>) offer);
                    Integer quantity = (Integer) offerMap.get("quantity");
                    Integer offerUnitPrice = (Integer) offerMap.get("unitPrice");
                    Integer freebieUnit = (Integer) offerMap.get("freebieUnit");
                    Integer frequency = (Integer) offerMap.get("frequency");
                    ItemType offerItemType = ItemType.forName((String) offerMap.get("itemType"));
                    OfferType offerType = OfferType.forName((String) offerMap.get("offerType"));
                    newData.put(
                            itemType, new ItemPrice(unitPrice,
                                    new SpecialOffers.SpecialOffersBuilder()
                                            .withOffer(quantity, offerItemType, offerUnitPrice, offerType, freebieUnit, frequency)
                                            .build())
                    );
                }
            } else {
                newData.put(itemType, new ItemPrice(unitPrice));
            }
        }
        return newData;
    }
}

