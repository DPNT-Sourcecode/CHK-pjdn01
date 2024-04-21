package befaster.solutions.CHK;

import java.util.List;

public class SpecialOffers {
    private final List<Offer> offers;

    public SpecialOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public SpecialOffers withOffer(int quantity, ItemType itemType, int unitPrice, OfferType offerType) {
        Offer offer = new Offer(quantity, itemType, unitPrice, offerType);
        offers.add(offer);
        return this;
    }

}

