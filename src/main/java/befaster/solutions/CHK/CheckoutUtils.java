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
}



