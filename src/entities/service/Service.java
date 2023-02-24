package entities.service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Service {
    protected static final Scanner SCANNER = new Scanner(System.in);

    protected static double askDouble(String message){
        double valor = -1;
        try{
            System.out.println(message);
            valor = SCANNER.nextDouble();
            SCANNER.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        if(valor <= 0){
            System.out.println("Input inválido!");
            return askDouble(message);
        }else{
            return valor;
        }
    }

    protected static int askInt(String message){
        int valor = -1;
        try{
            System.out.println(message);
            valor = SCANNER.nextInt();
            SCANNER.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        if(valor <= 0){
            System.out.println("Input inválido!");
            return askInt(message);
        }else{
            return valor;
        }
    }

    protected static String askString(String message){
        String valor = "";
        try{
            System.out.println(message);
            valor = SCANNER.nextLine();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
        if(valor.equals("")){
            System.out.println("Input inválido!");
            return askString(message);
        }else{
            return valor;
        }
    }
}
