/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ordinario;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cristina
 */
public class Ordinario {

    private static ArrayList<Autora> listaDeAutoras;

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
                        listaDeAutoras = DBManager.mapeaAutoras();
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

    public static void imprimirListaDeAutoras(ArrayList<Autora> unaListaDeAutoras) {
        for (Autora autora : unaListaDeAutoras) {
            System.out.println(autora.toString());
        }
    }

    public static boolean menuPrincipal() {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Mostar mis autoras");
        System.out.println("2. Mostrar la autora con más premios");
        System.out.println("3. Mostrar el número de autoras por país de residencia");
        System.out.println("4. Mostrar el número de autoras por campo de trabajo");
        System.out.println("5. Añadir una autora");
        System.out.println("6. Mostrar una autora en concreto");

        System.out.println("7. Salir");
        try {
            int opcion = pideInt("Elige una opción: ");
            switch (opcion) {
                case 1:
                    imprimirListaDeAutoras(listaDeAutoras);
                    return false;
                case 2:
                    imprimirAutorasConMasPremios();
                    return false;
                case 3:
                    imprimirNumAutorasPorCampoX(7);
                    return false;
                case 4:
                    imprimirNumAutorasPorCampoX(8);
//                    //Aquí pruebo el resto de llamadas a esta función                  
//                    imprimirNumAutorasPorCampoX(1);
//                    imprimirNumAutorasPorCampoX(2);
//                    imprimirNumAutorasPorCampoX(3);
//                    imprimirNumAutorasPorCampoX(4);
//                    imprimirNumAutorasPorCampoX(5);
//                    imprimirNumAutorasPorCampoX(6);
//
//                    //Estas muestran unmensaje de error
//                    imprimirNumAutorasPorCampoX(9);
//                    imprimirNumAutorasPorCampoX(0);

                    return false;
                case 5:
                    return false;
                case 6:
                    mostrarUnaAutoraporID();
                    return false;
                case 7://salir del bucle
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

    static void imprimirAutorasConMasPremios() {
        System.out.println("El número máximo de premios recibidos es " + numMaxDePremios());
        //Vamos aobtener un objeto opcional, que puede tener valor o no,
        //Así me obligo a verificarlo
        Optional<ArrayList<Autora>> posiblesAutoras = autorasConMasPremios();
        if (posiblesAutoras.isPresent()) {
            imprimirListaDeAutoras(posiblesAutoras.get());
        } else {
            System.out.println("No hay autoras con premios");
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

    private static Integer numMaxDePremios() {
        // Obtener el máximo número de premios
        int maxPremios = 0;
        for (Autora autora : listaDeAutoras) {
            if (autora.getPremiosRecibidos() > maxPremios) {
                maxPremios = autora.getPremiosRecibidos();
            }
        }
        return maxPremios;
    }

    private static Optional<ArrayList<Autora>> autorasConMasPremios() {
        Integer maximo = numMaxDePremios();
        ArrayList<Autora> lasMasPremiadas = new ArrayList<Autora>();
        for (Autora autora : listaDeAutoras) {
            if (autora.getPremiosRecibidos() == maximo) {
                lasMasPremiadas.add(autora);
            }
        }
        //Si intentas devolverlo directamente, no compila
//        return lasMasPremiadas;
        if (lasMasPremiadas.isEmpty()) {
            return Optional.empty(); // Si no hay autoras, retornamos Optional vacío
        } else {
            return Optional.of(lasMasPremiadas); // Si hay autoras, retornamos Optional con el ArrayList
        }
    }

    public static void mostrarUnaAutoraporID() {
        Autora autoraEncontrada = null;
        Integer id = pideInt("Escribe el id de la autora: ");
        for (Autora autora : listaDeAutoras) {
            if (autora.getId() == id) {
                autoraEncontrada = autora;
                //Si la encuentro, salgo del bucle
                break;
            }
        }
        //Verifico que ha encontrado una autora
        if (autoraEncontrada != null) {
            System.out.println(autoraEncontrada.toString());
            autoraEncontrada.crea();
        } else {
            System.out.println("No hemos encontrado autora con el ID " + id);
        }
    }

    public static void imprimirNumAutorasPorCampoX(int numDeCampo) {
        HashMap<String, Integer> ocurrenciasPorCampo = new HashMap<>();
        //Primero recorro listaDeAutoras para ir contabilizando en "cuenta"
        for (Autora autora : listaDeAutoras) {
            String clave;
            try {
                clave = autora.getCampoXToString(numDeCampo);
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Ha introducido " + numDeCampo);
                return;
                //No debe seguir intentando imprimir Autoras por campo X
            }
            if (ocurrenciasPorCampo.containsKey(clave)) {
                // Si la clave existe, obtener el valor actual y sumar uno
                int valorActual = ocurrenciasPorCampo.get(clave);
                ocurrenciasPorCampo.put(clave, valorActual + 1);
            } else {
                // Si la clave no existe, agregarla con valor 1
                ocurrenciasPorCampo.put(clave, 1);
            }
        }
        //Imprime el número de Autoras por cada ocurrencia del campo X
        imprimirHashMap(ocurrenciasPorCampo);
    }

    public static void imprimirHashMap(HashMap<String, Integer> hashMap) {
        System.out.println("");
        //Para cada entrada del HashMap, imprime la clave y el valor
        for (HashMap.Entry<String, Integer> entrada : hashMap.entrySet()) {
            String clave = entrada.getKey();
            int valor = entrada.getValue();
            System.out.println(clave + ": " + valor);
        }
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
