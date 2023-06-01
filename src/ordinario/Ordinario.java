/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ordinario;

import java.util.Scanner;
/**
 *
 * @author cristina
 */
public class Ordinario {

    public static int pideInt(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                Scanner sc = new Scanner(System.in);
                int valor = sc.nextInt();
                //in.nextLine();
                return valor;
            } catch (Exception e) {
                System.out.println("No has introducido un número entero. Vuelve a intentarlo.");
            }
        }
    }

    public static String pideLinea(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                Scanner sc = new Scanner(System.in);
                String linea = sc.nextLine();
                return linea;
            } catch (Exception e) {
                System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        DBManager.loadDriver();
        DBManager.connect();
        DBManager.printTablaAutoras();
    }

}
