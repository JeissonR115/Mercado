package login;

import Users.Admin;

import java.util.List;
import java.util.Scanner;

public  class Login {
    private static Admin user= null;
    List<Admin> listUsers;
    public Login(List<Admin> listUsers) {
        this.listUsers = listUsers;
    }
    public static boolean verifyCredentials(List<Admin> listUsers) {
        Scanner scanner = new Scanner(System.in);
        boolean longinStatus = false ,userFound = false;
        int numberOfAttempts = 3;
        String userNameEntered,passwordEntered;

        while (!userFound){
            System.out.println("*** \tLOGIN\t ***");
            System.out.println("(si quieres salir escribe \"exit\")");
            System.out.print("\tEscribe el usuario: ");
            userNameEntered =  scanner.nextLine();
            if (userNameEntered.equals("exit") ) return false;
            for (Admin usr : listUsers ){
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
        while (!longinStatus && numberOfAttempts > 0){
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

        return longinStatus;
    }
    public boolean verifyCredentials(){
        return verifyCredentials(listUsers);
    }
    public static Admin getUser(){return user;}
}
