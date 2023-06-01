/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ordinario;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author cristina
 */
public class Ordinario {

    private static ArrayList <Autora> listaDeAutoras;
    
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

    public static boolean menuEntradaDeDatos() {
        System.out.println("");
        System.out.println("ELIGE ENTRADA DE DATOS");
        System.out.println("1. Base de Datos");
        System.out.println("2. Archivo CSV");
        try {
            int opcion = pideInt("Elige una opción: ");
            switch (opcion) {
                case 1:
                    if (DBManager.loadDriver() && DBManager.connect()) {
                        //leo dela BD y cargo en estructura
                        listaDeAutoras=DBManager.mapeaAutoras();
                        DBManager.close();
                        return true;
                    }
                    ;
                case 2:
                    //Aquí llamaríamos a leer del csv();
                    return false;//por ahora simulo que noleo
                default:
                    System.out.println("Opción elegida incorrecta");
                    return false;
            }

        } catch (Exception ex) {
            System.out.println("Escribe una opción numérica válida");
            return false;
        }
    }

    public static boolean menuPrincipal() {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Mostar mis autoras");
        System.out.println("2. Nuevo contacto");
        System.out.println("3. Modificar contacto");
        System.out.println("4. Eliminar contacto");
        System.out.println("5. Salir");
        try {
            int opcion = pideInt("Elige una opción: ");
            switch (opcion) {
                case 1:
                    for (Autora autora: listaDeAutoras){
                        System.out.println(autora.toString());
                    }
                    return false;
                case 2:
                    return false;
                case 3:
                    return false;
                case 4:
                    return false;
                case 5:
                    return true;
                default:
                    System.out.println("Opción elegida incorrecta");
                    return false;
            }

        } catch (Exception ex) {
            System.out.println("Escribe una opción numérica válida");
            return false;
        }

    }

    public static Autora creaAutora(Integer id, String nombre, String apellidos, String alias, Date birthday, Integer premiosRecibidos, String paisDeResidencia, String areaDeTrabajo) {
        Autora autora;
        switch (areaDeTrabajo) {
            case "Poeta":
            case "Escritora":
            case "Periodista":
                autora = new Escritora(id, nombre, apellidos, alias, birthday, premiosRecibidos, paisDeResidencia, areaDeTrabajo);
                break;
            case "Científica":
                autora = new Cientifica(id, nombre, apellidos, alias, birthday, premiosRecibidos, paisDeResidencia, areaDeTrabajo);
                break;
            default:
                autora = new Creadora(id, nombre, apellidos, alias, birthday, premiosRecibidos, paisDeResidencia, areaDeTrabajo);
        }
        return autora;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

//        DBManager.loadDriver();
//        DBManager.connect();
//        DBManager.printTablaAutoras();
        //Entraremos al Menú Principal si se carga el driver y se conecta a la BD
        if (menuEntradaDeDatos()) {
            boolean salir = false;
            while (!salir) {
                salir = menuPrincipal();
            }
        }
    }

}
