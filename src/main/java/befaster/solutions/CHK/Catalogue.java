package befaster.solutions.CHK;

import java.io.Serializable;
import java.util.Map;

public class Catalogue implements Serializable {
    Map<ItemType, ItemPrice>  catalogue;

    public Map<ItemType, ItemPrice> getCatalogue() {
        return catalogue;
    }
}
