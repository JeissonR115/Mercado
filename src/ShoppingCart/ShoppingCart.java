package ShoppingCart;
import java.util.*;

public class ShoppingCart {
    private List<String> items;

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
    public void startShopping() {
        System.out.println("Bienvenido/a al carrito de compras.");

        boolean shopping = true;
        Scanner scanner = new Scanner(System.in);

        while (shopping) {
            System.out.println("Opciones:");
            System.out.println("1. Agregar un artículo");
            System.out.println("2. Eliminar un artículo");
            System.out.println("3. Ver carrito de compras");
            System.out.println("4. Salir");

            System.out.print("Ingrese el número de opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea después de leer el número de opción

            switch (option) {
                case 1:
                    System.out.print("Ingrese el nombre del artículo a agregar: ");
                    String itemToAdd = scanner.nextLine();
                    addItem(itemToAdd);
                    System.out.println("Artículo agregado al carrito.");
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del artículo a eliminar: ");
                    String itemToRemove = scanner.nextLine();
                    removeItem(itemToRemove);
                    System.out.println("Artículo eliminado del carrito.");
                    break;
                case 3:
                    showCart();
                    break;
                case 4:
                    shopping = false;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese un número válido.");
                    break;
            }
        }

        System.out.println("Gracias por usar el carrito de compras. ¡Hasta luego!");
    }

}
