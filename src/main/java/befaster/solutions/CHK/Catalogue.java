package befaster.solutions.CHK;

import java.io.Serializable;
import java.util.Map;

public class Catalogue implements Serializable {
    Map<Object, Object>  catalogue;

    public Map<Object, Object> getCatalogue() {
        return catalogue;
    }
}

