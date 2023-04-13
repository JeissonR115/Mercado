package ui;
import Users.*;
import java.util.*;
import static other.Login.*;

public class UIMenu {

    public static void showMenu(){
        System.out.println("Bienbeniso a Supermercado Ingenieros");
        System.out.println("Quien eres?");
        int response;
        do {
            System.out.println("1. Cliente");
            System.out.println("2. Trabajador");
            System.out.println("0. Salir");

            Scanner sc = new Scanner(System.in);
            response = Integer.parseInt(sc.nextLine());

            switch (response) {
                case 1 -> showClientMenu();
                case 2 -> {
                    UserDatabase userDb = new UserDatabase("./src/data/listUsers.txt");
                    List<Worker> listUsers = userDb.getUsers();
                    userDb.updateFile();
                    if (verifyCredentials(listUsers)) {
                        showMenuWorker(getUser());
                    } else {
                        System.out.println("Adios :)");
                    }
                }
                case 0 -> System.out.println("Gracias por la visita");
                default -> System.out.println("Porfavor selecione la respuesta correcta");
            }
        }while (response != 0);
    }
    static void showClientMenu(){
        int response;
        do {
            System.out.println("\n\n");
            System.out.println("Cliente");
            System.out.println("1. carrito");
            System.out.println("2. pagar");
            System.out.println("0. Return");

            Scanner sc = new Scanner(System.in);
            response = Integer.parseInt(sc.nextLine());

            switch (response) {
                case 1 -> System.out.println("shoppingCart()");
                case 2 -> System.out.println("pay()");
                case 0 -> showMenu();
            }
        }while (response != 0);
    }
    static void showMenuWorker(Worker user){
        System.out.println("\n");
        switch (user.getUserType()) {
            case "admin" -> System.out.println("showMenuAdmin\n");
            case "cashier" -> System.out.println("showMenuCashier\n");
            default -> {
                System.out.println("no he podido encontrar \"" + user.getUserType() + "\" como tipo de usuario.");
                System.out.println("los tipos de usuariios son :");
                System.out.println("\t* client(no nesesita registro)\n\t* cashier\n\t* admin");
                System.out.println();
            }
        }

    }
    private void showMenuAdmin(){}
    private void showMenuCashier(){}
}