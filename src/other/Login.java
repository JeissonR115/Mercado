package other;

import Users.User;
import Users.Worker;

import java.util.*;
public  class Login {
    private String userType ;
    public Login() {
    }
    public static boolean verifyCredentials(List<Worker> listUsers) {
        Scanner scanner = new Scanner(System.in);
        int numberOfAttempts = 3;
        boolean longinStaus = false;
        boolean userFound = false;
        Worker user = null;

        String userNameEntered,passwordEntered;

        while (numberOfAttempts > 0 && !longinStaus){
            System.out.println("si quieres salir escribe \"exit\"");
            System.out.print("\tEscribe el usuario: ");
            userNameEntered =  scanner.nextLine();
            if (userNameEntered.equals("exit") ){
                numberOfAttempts = 0;
                return false;
            }
            for (Worker usr : listUsers ){
                if(userNameEntered.equals(usr.getName())){
                    user = usr;
                    userFound = true;
                }
            }
            if(userFound){

                System.out.println("Hola "+ user.getName());
                System.out.print("\tEscribe la contrasenna: ");
                passwordEntered = scanner.nextLine();

                if (passwordEntered.equals(user.getPassword() )) {
                    System.out.println("contrasenna correcta");
                    longinStaus = true;
                } else {
                    System.out.println("contrasenna incorrecta");
                    numberOfAttempts --;
                    if ( numberOfAttempts != 0){
                        System.out.println("\nTe quedan "+ numberOfAttempts+" intentos");
                    }
                }

            } else{
                System.out.println("Usuario no encontrado");
                System.out.println("-------------------------------\n");
            }

        }
        return longinStaus;
    }
}
