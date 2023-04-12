package other;

import Users.User;
import Users.Worker;

import java.util.*;
public  class Login {
    private String userType ;
    private static Worker user= null;
    public Login() {
    }
    public static boolean verifyCredentials(List<Worker> listUsers) {
        Scanner scanner = new Scanner(System.in);
        boolean longinStaus = false ,userFound = false;
        int numberOfAttempts = 3;
        String userNameEntered,passwordEntered;

        while (!userFound){
            System.out.println("si quieres salir escribe \"exit\"");
            System.out.print("\tEscribe el usuario: ");
            userNameEntered =  scanner.nextLine();
            if (userNameEntered.equals("exit") ) return false;
            for (Worker usr : listUsers ){
                if(userNameEntered.equals(usr.getName())){
                    user = usr;
                    userFound = true;
                }
            }
            if(!userFound) {
                System.out.println("Usuario no encontrado");
                System.out.println("-------------------------------\n");
            }
        }
        while (!longinStaus && numberOfAttempts > 0){
            System.out.println("Hola "+ user.getName());
            System.out.print("\tEscribe la contrasenna: ");
            passwordEntered = scanner.nextLine();

            if (passwordEntered.equals(user.getPassword() )) {
                System.out.println("contrasenna correcta");
                return true;
            } else {
                System.out.println("contrasenna incorrecta");
                numberOfAttempts --;
                if ( numberOfAttempts == 0)return false;
                else System.out.println("\nTe quedan " + numberOfAttempts + " intentos");
            }
        }

        return longinStaus;
    }
    public static Worker getUser(){return user;}
}
