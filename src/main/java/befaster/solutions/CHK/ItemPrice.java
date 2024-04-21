package befaster.solutions.CHK;

import java.util.Optional;

public class ItemPrice {
    private final Double unitPrice;
    private final Double specialOfferPrice;
    private final Integer specialOfferQuantity;

    public ItemPrice(Double unitPrice, Double specialOfferPrice, Integer specialOfferQuantity) {
        this.unitPrice = unitPrice;
        this.specialOfferPrice = specialOfferPrice;
        this.specialOfferQuantity = specialOfferQuantity;
    }

    public ItemPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        this.specialOfferPrice = Double.NaN;
        this.specialOfferQuantity = null;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Optional<Double> getSpecialOfferPrice() {
        return !Double.isNaN(specialOfferPrice) ? Optional.of(specialOfferPrice) : Optional.empty();
    }

    public Optional<Integer> getSpecialOfferQuantity() {
        return specialOfferQuantity != null ? Optional.of(specialOfferQuantity) : Optional.empty();
    }
}


