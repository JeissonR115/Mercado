package ui;

import Users.Admin;
import database.Database;
import ShoppingCart.ShoppingCart;
import login.Login;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static login.Login.getUser;

public class UIMenu {
    public UIMenu(){

    }
    public static void runMenuWithOptions(List<String> options, List<Runnable> actions, String textReturn) {
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
    public void showMenu() {
        System.out.println("Bienvenido a Supermercado Ingenieros");
        System.out.println("Quién eres?");

        List<String> mainMenuOptions = Arrays.asList("Cliente", "Trabajador");
        List<Runnable> mainMenuActions = Arrays.asList(
                this::showClientMenu,
                this::selectUserMenu
        );
        runMenuWithOptions(mainMenuOptions, mainMenuActions, "(X) Salir de la App");
    }
    private void showClientMenu(){
        ShoppingCart shoppingCart = new ShoppingCart();
        UIShoppingCart uiShoppingCart = new UIShoppingCart(shoppingCart);
        List<String> options = Arrays.asList("carrito", "pagar");
        List<Runnable> actions = Arrays.asList(
                uiShoppingCart::mainMenu,
                () -> System.out.println("UIPay()")
        );
        runMenuWithOptions(options, actions,"(X) Salir del Menu");
    }
    private void selectUserMenu(){
        Database<Admin> userDb = new Database<>("./data/listUsers.txt", Admin.class);
        List<Admin> listUsers = userDb.getObjects();
        userDb.readObjectFromFile();
        System.out.println("\n");
        Login newLogin = new Login(listUsers);
        if (newLogin.verifyCredentials()){
            Admin user = getUser();
            switch (user.getUserType()) {
                case "admin" -> showAdminMenu();
                case "cashier" -> showCashierMenu();
                default -> {
                    System.out.println("no he podido encontrar \"" + user.getUserType() + "\" como tipo de usuario.");
                    System.out.println("los tipos de usuarios son :");
                    System.out.println("\t* client(no necesita registro)\n\t* cashier\n\t* admin");
                    System.out.println();
                }
            }
        }

    }
    private void showAdminMenu(){

        List<String> options = Arrays.asList("Bodega", "Empleados");
        List<Runnable> actions = Arrays.asList(
                () -> System.out.println("UIStore()"),
                () -> System.out.println("UIWorkers()")
        );
        runMenuWithOptions(options, actions,"(X) Salir del Menu");
    }
    private void showCashierMenu(){
        List<String> options = Arrays.asList("Registrar productos","Remover Productos","cobrar");
        List<Runnable> actions = Arrays.asList(
                () -> System.out.println("Registrar productos"),
                () -> System.out.println("Remover Productos"),
                () -> System.out.println("cobrar")
        );
        runMenuWithOptions(options, actions,"(X) Salir del Menu");
    }
}
