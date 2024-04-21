package befaster.solutions.CHK.model;

import java.util.Optional;

public class ItemCheckoutPrice {
    private final Freebies freebies;
    private final Integer price;

    public ItemCheckoutPrice(Integer price, Freebies freebies) {
        this.freebies = freebies;
        this.price = price;
    }

    public ItemCheckoutPrice(Integer price) {
        this.freebies = null;
        this.price = price;
    }

    public Optional<Freebies> getFreebies() {
        return freebies != null ? Optional.of(freebies) : Optional.empty();
    }

    public Integer getPrice() {
        return price;
    }
}
