package befaster.solutions.CHK;

import java.util.Map;

public class CheckoutUtils {

    public static final Map<ItemType, ItemPrice> ItemToPriceMap =
            Map.of(
                    ItemType.A, new ItemPrice(50, 130, 3),
                    ItemType.B, new ItemPrice(30, 45, 2),
                    ItemType.C, new ItemPrice(20),
                    ItemType.D, new ItemPrice(15)
            );
    public static final Map<ItemType, ItemPrice> ItemToPriceMap2 =
            Map.of(
                    ItemType.A, new ItemPrice(50,
                            new SpecialOffers.SpecialOffersBuilder()
                                    .withOffer(3, ItemType.A, 130, OfferType.DISCOUNT)
                                    .withOffer(5, ItemType.A, 200, OfferType.DISCOUNT)
                                    .build()
                    ),
                    ItemType.B, new ItemPrice(30,
                            new SpecialOffers.SpecialOffersBuilder()
                                    .withOffer(2, ItemType.B, 45, OfferType.DISCOUNT)
                                    .build()
                    ),
                    ItemType.C, new ItemPrice(20),
                    ItemType.D, new ItemPrice(15),
                    ItemType.E, new ItemPrice(40,
                            new SpecialOffers.SpecialOffersBuilder()
                                    .withOffer(2, ItemType.B, 0, OfferType.FREEBIES)
                                    .build()
                    )
            );
}
