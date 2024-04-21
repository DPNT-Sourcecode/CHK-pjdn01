package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.List;

public class SpecialOffers {
    private final List<Offer> offers;

    public SpecialOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public SpecialOffers(SpecialOffersBuilder specialOffersBuilder) {
        this.offers = specialOffersBuilder.offers;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public static class SpecialOffersBuilder {
        protected final List<Offer> offers = new ArrayList<>();

        public SpecialOffersBuilder withOffer(int quantity, ItemType itemType, int unitPrice, OfferType offerType) {
            Offer offer = new Offer(quantity, itemType, unitPrice, offerType);
            this.offers.add(offer);
            return this;
        }

        public SpecialOffersBuilder withOffer(int quantity, ItemType itemType, int unitPrice, OfferType offerType, int freebieUnit) {
            Offer offer = new Offer(quantity, itemType, unitPrice, offerType, freebieUnit);
            this.offers.add(offer);
            return this;
        }

        public SpecialOffersBuilder withOffer(int quantity, ItemType itemType, int unitPrice, OfferType offerType, int freebieUnit, int frequency) {
            Offer offer = new Offer(quantity, itemType, unitPrice, offerType, freebieUnit, frequency);
            this.offers.add(offer);
            return this;
        }


        public SpecialOffers build() {
            return new SpecialOffers(this);
        }
    }


}

