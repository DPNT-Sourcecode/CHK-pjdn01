package befaster.solutions.CHK;

import java.util.Optional;

public class ItemPrice {
    private final Integer unitPrice;
    private final SpecialOffers specialOffers;

    public ItemPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        this.specialOffers = null;
    }

    public ItemPrice(Integer unitPrice, SpecialOffers specialOffers) {
        this.unitPrice = unitPrice;
        this.specialOffers = specialOffers;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Optional<SpecialOffers> getSpecialOffers() {
        return specialOffers != null ? Optional.of(specialOffers) : Optional.empty();
    }
}

