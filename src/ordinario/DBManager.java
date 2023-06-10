package ordinario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

/**
 *
 * @author lionel Adaptación de Cristina
 */
public class DBManager {

    // Conexión a la base de datos
    private static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static String IP_ADDRES_HOST = "localhost";
    private static final String IP_ADDRESS_IES = "10.230.109.71";
    private static final String IP_ADDRESS_HOME = "192.168.1.159";
    private static final String IP_ADDRESS_DEFAULT = "192.168.11.65";



    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "Autoras";

    private static String DB_URL;

    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    // Configuración de la tabla contacto
    private static final String DB_CONT = "Autoras";
    private static final String DB_CONT_SELECT = "SELECT * FROM " + DB_CONT;
    private static final String DB_CONT_ID = "id";
    private static final String DB_CONT_NOM = "nombre";
    private static final String DB_CONT_APE = "apellidos";
    private static final String DB_CONT_ALIAS = "alias";
    private static final String DB_CONT_BIRTHDAY = "fecha_nacimiento";
    private static final String DB_CONT_PREMIOS = "premios_recibidos";
    private static final String DB_CONT_PAIS_RES = "pais_residencia";
    private static final String DB_CONT_AREA_TRABAJO = "area_trabajo";

    //////////////////////////////////////////////////
    // MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
    //////////////////////////////////////////////////
    ;
    
    /**
     * Intenta cargar el JDBC driver.
     * @return true si pudo cargar el driver, false en caso contrario
     */
    public static boolean loadDriver() {
        try {
            System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("OK!");
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @return true si pudo conectarse, false en caso contrario
     */
    public static boolean connect() {
        try {
            try {
                //Leemos del teclado para elegir la IP del servidor en casa o en el IES
                System.out.println("Si estás en casa escribe H de Home, en el instituto I de IES");
                Scanner sc = new Scanner(System.in);
                String respuesta = sc.next("[hHiIrR]");
                switch (respuesta.toUpperCase()) {
                    case "H":
                        IP_ADDRES_HOST = IP_ADDRESS_HOME;
                        break;
                    case "I":
                        IP_ADDRES_HOST = IP_ADDRESS_IES;
                        break;
                    default: IP_ADDRES_HOST = IP_ADDRESS_DEFAULT;
;
                }
            } catch (Exception ex) {
                System.out.println("Te empeñas en escribir cualquier cosa en vez de una letra H o I");
                return false;
            }
            DB_URL = "jdbc:mysql://" + IP_ADDRES_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";

            System.out.print("Conectando a la base de datos "+DB_NAME+" en la IP "+IP_ADDRES_HOST+" ... ");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Comprueba la conexión y muestra su estado por pantalla
     *
     * @return true si la conexión existe y es válida, false en caso contrario
     */
    public static boolean isConnected() {
        // Comprobamos estado de la conexión
        try {
            if (conn != null && conn.isValid(0)) {
                System.out.println(DB_MSQ_CONN_OK);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public static void close() {
        try {
            System.out.print("Cerrando la conexión...");
            conn.close();
            System.out.println("OK!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA CONTACTOS
    //////////////////////////////////////////////////
    ;
    
    // Devuelve 
    // Los argumentos indican el tipo de ResultSet deseado
    /**
     * Obtiene toda la tabla contactos de la base de datos
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     */
    public static ResultSet getTablaAutoras(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_CONT_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Obtiene toda la tabla contactos de la base de datos
     *
     * @return ResultSet (por defecto) con la tabla, null en caso de error
     */
    public static ResultSet getTablaAutoras() {
        return DBManager.getTablaAutoras(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Imprime por pantalla el contenido de la tabla contactos
     */
    public static void printTablaAutoras() {
        try {
            ResultSet rs = DBManager.getTablaAutoras(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            while (rs.next()) {
                int id = rs.getInt(DB_CONT_ID);
                String n = rs.getString(DB_CONT_NOM);
                String a = rs.getString(DB_CONT_APE);
                String alias = rs.getString(DB_CONT_ALIAS);
                Integer premios = rs.getInt(DB_CONT_PREMIOS);
                Date fecha = rs.getDate(DB_CONT_BIRTHDAY);
                String residencia = rs.getString(DB_CONT_PAIS_RES);
                String trabajo = rs.getString(DB_CONT_AREA_TRABAJO);
                System.out.println(id + "\t" + n + "\t" + a + "\t" + alias + "\t" + premios + "\t" + fecha + "\t" + residencia + "\t" + trabajo);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Autora> mapeaAutoras() {

        ArrayList<Autora> listaDeAutoras = new ArrayList();
        try {
            ResultSet rs = DBManager.getTablaAutoras(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            while (rs.next()) {
                //Para cada registro, añade una autora al ArrayList
                int id = rs.getInt(DB_CONT_ID);
                String nombre = rs.getString(DB_CONT_NOM);
                String apellidos = rs.getString(DB_CONT_APE);
                String alias = rs.getString(DB_CONT_ALIAS);
                Integer premios = rs.getInt(DB_CONT_PREMIOS);
                Date fecha = rs.getDate(DB_CONT_BIRTHDAY);
                LocalDate fechaLocal =fecha.toLocalDate();
                String residencia = rs.getString(DB_CONT_PAIS_RES);
                String trabajo = rs.getString(DB_CONT_AREA_TRABAJO);
                Autora nuevaAutora = Ordinario.creaAutora(id, nombre, apellidos, alias, fechaLocal, premios, residencia, trabajo);
                listaDeAutoras.add(nuevaAutora);
            }
            rs.close();
            return listaDeAutoras;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE UN SOLO CONTACTO
    //////////////////////////////////////////////////
    ;
    
    /**
     * Solicita a la BD la autora con id indicado
     * @param id id de la autora
     * @return ResultSet con el resultado de la consulta, null en caso de error
     */
    public static ResultSet getAutora(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = DB_CONT_SELECT + " WHERE " + DB_CONT_ID + "='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe la autora
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos la autora
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Comprueba si en la BD existe la autora con id indicado
     *
     * @param id id de la autora
     * @return verdadero si existe, false en caso contrario
     */
    public static boolean existsAutora(int id) {
        try {
            // Obtenemos la autora
            ResultSet rs = getAutora(id);

            // Si rs es null, se ha producido un error
            if (rs == null) {
                return false;
            }

            // Si no existe primer registro
            if (!rs.first()) {
                rs.close();
                return false;
            }

            // Todo bien, existe la autora
            rs.close();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Imprime los datos de la autora con id indicado
     *
     * @param id id de la autora
     */
    public static void printAutora(int id) {
        try {
            // Obtenemos la autora
            ResultSet rs = getAutora(id);
            if (rs == null || !rs.first()) {
                System.out.println("Contacto " + id + " NO EXISTE");
                return;
            }

            // Imprimimos su información por pantalla
            int aid = rs.getInt(DB_CONT_ID);
            String nombre = rs.getString(DB_CONT_NOM);
            String apellidos = rs.getString(DB_CONT_APE);

            String alias = rs.getString(DB_CONT_ALIAS);
            Integer premios = rs.getInt(DB_CONT_PREMIOS);
            Date fecha = rs.getDate(DB_CONT_BIRTHDAY);
            String residencia = rs.getString(DB_CONT_PAIS_RES);
            String trabajo = rs.getString(DB_CONT_AREA_TRABAJO);
            System.out.println(aid + "\t" + nombre + "\t" + apellidos + "\t" + alias + "\t" + premios + "\t" + fecha + "\t" + residencia + "\t" + trabajo);

        } catch (SQLException ex) {
            System.out.println("Error al solicitar autora " + id);
            ex.printStackTrace();
        }
    }

    /**
     * Solicita a la BD insertar un nuevo registro
     *
     * @param nombre nombre de la autora
     * @param apellidos dirección de la autora
     * @return verdadero si pudo insertarlo, false en caso contrario
     */
    public static boolean insertAutora(String nombre, String apellidos) {
        try {
            // Obtenemos la tabla contactos
            System.out.print("Insertando contacto " + nombre + "...");
            ResultSet rs = DBManager.getTablaAutoras(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_CONT_NOM, nombre);
            rs.updateString(DB_CONT_APE, apellidos);
            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD modificar los datos de un contacto
     *
     * @param id id de la autora a modificar
     * @param nuevoNombre nuevo nombre de la autora
     * @param nuevosApellidos nuevo telefono de la autora
     * @return verdadero si pudo modificarlo, false en caso contrario
     */
    public static boolean updateAutoras(int id, String nuevoNombre, String nuevosApellidos) {
        try {
            // Obtenemos la autora
            System.out.print("Actualizando contacto " + id + "... ");
            ResultSet rs = getAutora(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo eliminamos
            if (rs.first()) {
                rs.updateString(DB_CONT_NOM, nuevoNombre);
                rs.updateString(DB_CONT_APE, nuevosApellidos);
                rs.updateRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } else {
                System.out.println("ERROR. ResultSet vacío.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD eliminar un contacto
     *
     * @param id id de la autora a eliminar
     * @return verdadero si pudo eliminarlo, false en caso contrario
     */
    public static boolean deleteAutora(int id) {
        try {
            System.out.print("Eliminando contacto " + id + "... ");

            // Obtenemos la autora
            ResultSet rs = getAutora(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("ERROR. ResultSet null.");
                return false;
            }

            // Si existe y tiene primer registro, lo eliminamos
            if (rs.first()) {
                rs.deleteRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } else {
                System.out.println("ERROR. ResultSet vacío.");
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
