package ui;
import ShoppingCart.*;
import Users.*;
import java.util.*;
import static other.Login.getUser;
import static other.Login.verifyCredentials;

public class UIMenu {
    static void runMenuWithOptions(List<String> options, List<Runnable> actions, String textReturn) {
        int numOptions = options.size();
        if (numOptions != actions.size()) {
            System.out.println("Error: Los arreglos de opciones y acciones no tienen la misma longitud");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println();
            for (int i = 0; i < numOptions; i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            System.out.println("0. " + textReturn);

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            try {
                int response = Integer.parseInt(input)-1;
                if (response+1 == 0) {
                    running = false;
                } else if (response < 0 || response  >= numOptions) {
                    System.out.println("Por favor seleccione una opción válida");
                } else {
                    actions.get(response).run();
                }
            } catch (NumberFormatException e) {
                if (input.equals("x") || input.equals("X") || input.equals("exit")){
                    running = false;
                } else {
                    System.out.println("Error: La entrada no es válida. Ingrese solo números.");
                }
            }
        }
    }

    public static void showMenu() {
        System.out.println("Bienvenido a Supermercado Ingenieros");
        System.out.println("Quién eres?");

        List<String> mainMenuOptions = Arrays.asList("Cliente", "Trabajador");
        List<Runnable> mainMenuActions = Arrays.asList(
                UIMenu::showClientMenu,
                UIMenu::showWorkerMenu
        );
        runMenuWithOptions(mainMenuOptions, mainMenuActions, "(X) Salir de la App");
    }
    static void showClientMenu(){
        ShoppingCart shoppingCart = new ShoppingCart();
        UIShoppingCart uiShoppingCart = new UIShoppingCart(shoppingCart);
        List<String> options = Arrays.asList("carrito", "pagar");
        List<Runnable> actions = Arrays.asList(
                uiShoppingCart::mainMenu,
                () -> System.out.println("UIpay()")
        );
        runMenuWithOptions(options, actions,"(X) Salir del Menu");
    }
    static void showWorkerMenu(){
        UserDatabase userDb = new UserDatabase("./src/data/listUsers.txt");
        List<Worker> listUsers = userDb.getUsers();
        userDb.updateFile();
        System.out.println("\n");
        if (verifyCredentials(listUsers)){
            Worker user = getUser();
            switch (user.getUserType()) {
                case "admin" -> showAdminMenu();
                case "cashier" -> showCashierMenu();
                default -> {
                    System.out.println("no he podido encontrar \"" + user.getUserType() + "\" como tipo de usuario.");
                    System.out.println("los tipos de usuariios son :");
                    System.out.println("\t* client(no nesesita registro)\n\t* cashier\n\t* admin");
                    System.out.println();
                }
            }
        }

    }
    static void showAdminMenu(){

        List<String> options = Arrays.asList("Bodega", "Empleados");
        List<Runnable> actions = Arrays.asList(
                () -> System.out.println("UIStore()"),
                () -> System.out.println("UIWorkers()")
        );
        runMenuWithOptions(options, actions,"(X) Salir del Menu");
    }
    static void showCashierMenu(){
        List<String> options = Arrays.asList("Registrar productos","Remover Productos","cobrar");
        List<Runnable> actions = Arrays.asList(
                () -> System.out.println("Registrar productos"),
                () -> System.out.println("Remover Productos"),
                () -> System.out.println("cobrar")
        );
        runMenuWithOptions(options, actions,"(X) Salir del Menu");
    }
}