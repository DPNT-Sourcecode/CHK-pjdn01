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
                    Integer quantity = offerMap.get("quantity") != null ? (Integer) offerMap.get("quantity") : null;
                    Integer offerUnitPrice = offerMap.get("unitPrice") != null ? (Integer) offerMap.get("unitPrice") : null;
                    Integer frequency = offerMap.get("frequency") != null ? (Integer) offerMap.get("frequency") : null;
                    ItemType offerItemType = offerMap.get("itemType") != null ? ItemType.forName(((String) offerMap.get("itemType"))) : null;
                    OfferType offerType = offerMap.get("offerType") != null ? OfferType.forName(((String) offerMap.get("offerType"))) : null;
                    Integer freebieUnit = offerMap.get("freebieUnit") != null ? (Integer) offerMap.get("freebieUnit") : null;
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



