/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ordinario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author cristina
 */
public class Ordinario {

    private static ArrayList<Autora> listaDeAutoras;
    private static final String CSV_FILE_IN = "autoras.csv";

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
                    listaDeAutoras = mapeaAutorasDesdeCSV();
                    return true;//por ahora simulo que noleo
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
                    addAutora();
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

    public static Autora creaAutora(Integer id, String nombre, String apellidos, String alias, LocalDate birthday, Integer premiosRecibidos, String paisDeResidencia, String areaDeTrabajo) {
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

    private static Integer numMaxDeId() {
        // Obtener el máximo número de premios
        int maxId = 0;
        for (Autora autora : listaDeAutoras) {
            if (autora.getId() > maxId) {
                maxId = autora.getId();
            }
        }
        return maxId;
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
        //Primero recorro listaDeAutoras para ir contabilizando en "ocurrenciasPorCampo"
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

    public static void guardarDatosEnCSV() {
        FileWriter fichero;
        try {
            String nombreDeArchivo = "autoras_" + LocalDate.now() + "_" + LocalTime.now() + ".csv";
            System.out.println("El nombre por defecto del archivo de salida es " + nombreDeArchivo);
            String nombreAlternativo = pideLinea("Introduce otro nombre para el archivo o pulsa ENTER si aceptas ese nombre");
            if (nombreAlternativo.isEmpty()) {
                fichero = new FileWriter(nombreDeArchivo);
            } else {
                fichero = new FileWriter(nombreAlternativo);
            }

            for (Autora autora : listaDeAutoras) {
                fichero.write(autora.toStringCSV());
                fichero.write("\n");
            }
            fichero.close();
        } catch (IOException ex) {
            System.out.println("No pudo escribirse el archivo de salida");
        }

    }

    public static LocalDate pideFecha(String mensaje) {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        while (true) {
            String fechaString = pideLinea(mensaje + " (en formato yyyy-MM-dd) ");
            try {
                LocalDate fecha = LocalDate.parse(fechaString, formatter);
                return fecha;
            } catch (Exception e) {
                System.out.println("No pudo convertirse lo que introduciste a formato fecha. Vuelve a intentarlo");
            }
        }
    }

    public static void addAutora() {

        listaDeAutoras.add(creaAutora(numMaxDeId() + 1, pideLinea("Introduce nombre "), pideLinea("Introduce apelllidos "), pideLinea("Introduce alias "), pideFecha("Fecha de nacimiento "), pideInt("Número de premios "), pideLinea("Introduce país de residencia "), pideLinea("Introduce área de trabajo ")));
    }

    public static ArrayList<Autora> mapeaAutorasDesdeCSV() {

        ArrayList<Autora> lista = new ArrayList();

        String linea;
        String csvSeparator = ","; // Separador utilizado en el archivo CSV

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_IN))) {
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(csvSeparator);
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate fechaLocal;
                try {
                    Integer id = Integer.valueOf(campos[0]);
                    String nombre = campos[1];
                    String apellidos = campos[2];
                    String alias = campos[3];
                    fechaLocal = LocalDate.parse(campos[4], formatter);
                    Integer premios = 0;
                    //Si no tiene premios
                    if (!campos[5].isEmpty()) {
                        premios = Integer.valueOf(campos[5]);
                    }
                    String residencia = campos[6];
                    String trabajo = campos[7];
                    Autora nuevaAutora = creaAutora(id, nombre, apellidos, alias, fechaLocal, premios, residencia, trabajo);
                    lista.add(nuevaAutora);
                } catch (NumberFormatException ex) {
                    System.out.println("No pudo convertirse un string a Integer");
                } catch (Exception e) {
                    System.out.println("No pudo convertirse una fecha de nacimiento");
                    //Seguirá intentando leer autoras del archivo CSV
                }
            }//Termina de leer el fichero.
            br.close();
            System.out.println("Leídas autoras desde archivo CSV");
            } catch (FileNotFoundException e) {
            System.out.println("No puede leerse el archivo " + CSV_FILE_IN);
        } catch (IOException e) {
            System.out.println("No puede cerrarse el archivo de entrada " + CSV_FILE_IN);
        }
        return lista;//Devuelve lista vacía en caso de Excepción
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
            guardarDatosEnCSV();
        }
    }

}
