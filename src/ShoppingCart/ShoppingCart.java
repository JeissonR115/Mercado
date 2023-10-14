package ShoppingCart;
import java.util.*;

public class ShoppingCart {
    private final List<String> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }
    public void showCart() {
        System.out.println("Carrito de compras:");
        if (items.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            for (String item : items) {
                System.out.println("- " + item);
            }
        }
    }

}
