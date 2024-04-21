package befaster.solutions.CHK;

import java.util.Optional;

public class ItemPrice {
    private final Integer unitPrice;
    private final Integer specialOfferPrice;
    private final Integer specialOfferQuantity;
    private final SpecialOffers specialOffers;

    public ItemPrice(Integer unitPrice, Integer specialOfferPrice, Integer specialOfferQuantity) {
        this.unitPrice = unitPrice;
        this.specialOfferPrice = specialOfferPrice;
        this.specialOfferQuantity = specialOfferQuantity;
        this.specialOffers = null;
    }

    public ItemPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        this.specialOfferPrice = null;
        this.specialOfferQuantity = null;
        this.specialOffers = null;
    }

    public ItemPrice(Integer unitPrice, SpecialOffers specialOffers) {
        this.unitPrice = unitPrice;
        this.specialOffers = specialOffers;
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

    public Optional<SpecialOffers> getSpecialOffers() {
        return specialOffers != null ? Optional.of(specialOffers) : Optional.empty();
    }
}

