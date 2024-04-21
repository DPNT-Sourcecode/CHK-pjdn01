package befaster.solutions.CHK;

import java.util.Optional;

public class ItemPrice {
    private final Integer unitPrice;
    private final Integer specialOfferPrice;
    private final Integer specialOfferQuantity;

    public ItemPrice(Integer unitPrice, Integer specialOfferPrice, Integer specialOfferQuantity) {
        this.unitPrice = unitPrice;
        this.specialOfferPrice = specialOfferPrice;
        this.specialOfferQuantity = specialOfferQuantity;
    }

    public ItemPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        this.specialOfferPrice = null;
        this.specialOfferQuantity = null;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Optional<Integer> getSpecialOfferPrice() {
        return specialOfferPrice != null ? Optional.of(specialOfferPrice) : Optional.empty();
    }

    public Optional<Integer> getSpecialOfferQuantity() {
        return specialOfferQuantity != null ? Optional.of(specialOfferQuantity) : Optional.empty();
    }
}



