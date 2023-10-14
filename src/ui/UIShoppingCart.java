package ui;
import java.util.*;
import ShoppingCart.*;
import static ui.UIMenu.*;

public class UIShoppingCart {
    Scanner sc = new Scanner(System.in);
    private ShoppingCart cart;
    public UIShoppingCart(ShoppingCart cart){
        this.cart = cart;
    }
    
    public void mainMenu() {
        System.out.println("Bienvenido/a al carrito de compras.");
        List<String> options = Arrays.asList(
                "Agregar un artículo",
                "Eliminar un artículo",
                "Ver carrito de compras"
        );

        List<Runnable> actions = Arrays.asList(
                this::addItem,
                this::removeItem,
                () -> cart.showCart()
        );
        runMenuWithOptions(options, actions,"<-- Atras");
    }
    private void addItem(){
        System.out.print("Ingrese el nombre del artículo a agregar: ");
        String itemToAdd = sc.nextLine();
        cart.addItem(itemToAdd);
        System.out.println("Artículo agregado al carrito.");

    }
    private void removeItem(){
        System.out.print("Ingrese el nombre del artículo a eliminar: ");
        String itemToRemove = sc.nextLine();
        cart.removeItem(itemToRemove);
        System.out.println("Artículo eliminado del carrito.");
    }
}