import java.util.*;

public class ShoppingCart {
    private final List<Product> cartItems = new ArrayList<>();

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
    }

    public List<Product> getItems() {
        return cartItems;
    }

    public double getTotal() {
        return cartItems.stream().mapToDouble(p -> p.price).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}

