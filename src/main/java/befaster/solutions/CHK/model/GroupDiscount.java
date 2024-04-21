package befaster.solutions.CHK.model;

import java.util.List;

public class GroupDiscount {
    private Integer groupQuantity;
    private Integer unitPrice;

    public GroupDiscount(Integer groupQuantity, Integer unitPrice) {
        this.groupQuantity = groupQuantity;
        this.unitPrice = unitPrice;
    }

    public Integer getGroupQuantity() {
        return groupQuantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }
}
