package befaster.solutions.CHK.model;

import befaster.solutions.CHK.model.enums.ItemType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static befaster.solutions.CHK.model.Catalogue.GROUPS;
import static befaster.solutions.CHK.model.Catalogue.GROUP_DISCOUNT_MAP;

public class CheckoutUtils {
    public static final String filename = "D:\\akahuc\\Documents\\runner-for-java-windows\\accelerate_runner\\src\\main\\java\\befaster\\solutions\\CHK\\model\\itemCatalogue.json";

    public static Map<ItemType, Integer> getItemToCountMap(String skus, Map<ItemType, ItemPrice> catalogue, boolean getGroupMembers) {
        Map<ItemType, Integer> itemToCountMap = new HashMap<>();

        for (String str : skus.split("")) {
            ItemType itemType = ItemType.forName(str);
            ItemPrice itemPrice = catalogue.get(itemType);
            try {
                ItemType discountName = itemPrice.getSpecialOffers()
                        .orElseThrow().getOffers().iterator().next().getGroupDiscountName();
                if (discountName == null && !getGroupMembers) {
                    itemToCountMap.merge(itemType, 1, Integer::sum);
                } else if (discountName != null && getGroupMembers) {
                    itemToCountMap.merge(itemType, 1, Integer::sum);
                }
            } catch (Exception e) {
                System.out.println("unknown item found");
                itemToCountMap.merge(itemType, 1, Integer::sum);
            }
        }
        return itemToCountMap;
    }

    public static Map<ItemType, Group> buildGroup(String skus, Map<ItemType, ItemPrice> catalogue) {
        Map<ItemType, Group> groupMap = new HashMap<>();
        Map<ItemType, Integer> groupItems = getItemToCountMap(skus, catalogue, true);
        GROUPS.forEach(groupName -> {
            Map<ItemType, GroupMember> members = new HashMap<>();
            for (ItemType itemType : groupItems.keySet()) {
                ItemPrice itemPrice = catalogue.get(itemType);
                Optional<SpecialOffers> specialOffers = itemPrice.getSpecialOffers();
                if (specialOffers.isPresent()) {
                    Offer offer = specialOffers.get().getOffers().iterator().next();
                    if (offer.getGroupDiscountName() == groupName) {
                        Group group = groupMap.get(itemType);
                        if (group == null) {
                            members.put(itemType, new GroupMember(catalogue.get(itemType), groupItems.get(itemType)));
                            GroupDiscount discount = GROUP_DISCOUNT_MAP.get(groupName);
                            groupMap.put(groupName, new Group(discount.getUnitPrice(), discount.getGroupQuantity(), members));
                        } else {
                            group.addGroupMember(itemType, new GroupMember(catalogue.get(itemType), groupItems.get(itemType)));
                        }
                    }
                }
            }
        });
        return groupMap;
    }
}
