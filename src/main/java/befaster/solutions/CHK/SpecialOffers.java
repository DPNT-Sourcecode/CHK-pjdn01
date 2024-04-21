package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.List;

public class SpecialOffers {
    private final List<Offer> offers;

    public SpecialOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public SpecialOffers(SpecialOffersBuilder specialOffersBuilder) {
        this.offers = specialOffersBuilder.offerList;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public static class SpecialOffersBuilder {
        protected final List<Offer> offerList = new ArrayList<>();

        public SpecialOffersBuilder withOffers(List<Offer> offers) {
            this.offerList.addAll(offers);
            return this;
        }

        public SpecialOffersBuilder withOffer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType) {
            Offer offer = new Offer(quantity, itemType, unitPrice, offerType);
            this.offerList.add(offer);
            return this;
        }

        public SpecialOffersBuilder withOffer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit) {
            Offer offer = new Offer(quantity, itemType, unitPrice, offerType, freebieUnit);
            this.offerList.add(offer);
            return this;
        }

        public SpecialOffersBuilder withOffer(Integer quantity, ItemType itemType, Integer unitPrice, OfferType offerType, Integer freebieUnit, Integer frequency) {
            Offer offer = new Offer(quantity, itemType, unitPrice, offerType, freebieUnit, frequency);
            this.offerList.add(offer);
            return this;
        }


        public SpecialOffers build() {
            return new SpecialOffers(this);
        }
    }


}
