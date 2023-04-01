package ui;

import java.util.Scanner;

public class UIMenu {

    public static void showMenu(){
        System.out.println("Bienbeniso a Supermercado Ingenieros");
        System.out.println("Quien eres?");

        int response = 0;
        do {
            System.out.println("1. Cliente");
            System.out.println("2. Empleado");
            System.out.println("0. Salir");

            Scanner sc = new Scanner(System.in);
            response = Integer.valueOf(sc.nextLine());

            switch (response){
                case 1:
                    System.out.println("Doctor");
                    break;
                case 2:
                    response = 0;
                    showPatientMenu();

                    break;
                case 0:
                    System.out.println("Gracias por la visita");
                    break;
                default:
                    System.out.println("Porfavor selecione la respuesta correcta");
            }
        }while (response != 0);
    }

    static void showPatientMenu(){
        int response = 0;
        do {
            System.out.println("\n\n");
            System.out.println("Patient");
            System.out.println("1. Book an appointment");
            System.out.println("2. My appointments");
            System.out.println("0. Return");

            Scanner sc = new Scanner(System.in);
            response = Integer.valueOf(sc.nextLine());

            switch (response){
                case 1:
                    System.out.println("::Book an appointment");
                    break;
                case 2:
                    System.out.println("::My appointments");
                    break;
                case 0:
                    showMenu();
                    break;
            }
        }while (response != 0);
    }

    public static void showMenuWorker(){
        int response=0;
        System.out.println("\n\n");
        System.out.println("Patient");
        System.out.println("1. Book an appointment");
        System.out.println("2. My appointments");
        System.out.println("0. Return");
        do {
            switch (response){
                case 1:
                    System.out.println("::Book an appointment");
                    break;
                case 2:
                    System.out.println("::My appointments");
                    break;
                case 0:
                    showMenu();
                    break;
            }
        } while (response != 0);
    };
}