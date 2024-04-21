package befaster.solutions.CHK;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static befaster.solutions.CHK.Catalogue.*;
import static befaster.solutions.CHK.CheckoutCalculator.calculateTotalCost;

public class CheckoutSolution {

    private static final String filename = "D:\\akahuc\\Documents\\runner-for-java-windows\\accelerate_runner\\src\\main\\java\\befaster\\solutions\\CHK\\itemCatalogue.json";

    public static void main(String[] args) {
        Integer check = checkout("SXTYZ");
        System.out.println(check);
    }

    public static Integer checkout(String skus) {
        try {
            if (skus == null || skus.isEmpty()) {
                return 0;
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<Object, Object> data = mapper.readValue(new FileReader(filename), Map.class);
            Map<ItemType, ItemPrice> catalogue = buildCatalogue(data);
            Map<ItemType, Integer> itemToCountMap = getItemToCountMap(skus, catalogue, false);
            Map<ItemType, Group> groupMap = buildGroup(skus, catalogue);
            Integer totalCost = calculateTotalCost(itemToCountMap, groupMap, catalogue);
            return totalCost;
        } catch (Exception e) {
            System.out.println("Error occurred loading catalogue data");
            return -1;
        }
    }

    private static Map<ItemType, Integer> getItemToCountMap(String skus, Map<ItemType, ItemPrice> catalogue, boolean getGroupMembers) {
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

    private static Map<ItemType, Group> buildGroup(String skus, Map<ItemType, ItemPrice> catalogue) {
        Map<ItemType, Group> groupMap = new HashMap<>();
        Map<ItemType, Integer> groupItems = getItemToCountMap(skus, catalogue, true);
        GROUPS.forEach(groupName -> {
            Map<ItemType, GroupMember> members = new HashMap<>();
            for (ItemType itemType : groupItems.keySet()) {
                ItemPrice itemPrice = catalogue.get(itemType);
                Offer offer = itemPrice.getSpecialOffers().orElseThrow().getOffers().iterator().next();
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
        });
        return groupMap;
    }
}
