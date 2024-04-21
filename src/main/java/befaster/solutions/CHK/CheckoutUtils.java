package befaster.solutions.CHK;

import java.util.Map;

public class CheckoutUtils {

    public static final Map<ItemType, ItemPrice> ItemToPriceMap =
            Map.of(
                    ItemType.A, new ItemPrice(50d, 130d, 3d),
                    ItemType.B, new ItemPrice(30d, 45d, 2d),
                    ItemType.C, new ItemPrice(20d),
                    ItemType.D, new ItemPrice(15d)
            );
}

