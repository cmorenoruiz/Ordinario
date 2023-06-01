/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ordinario;

import java.util.Date;


/**
 *
 * @author cristina
 */
public abstract class Autora {
    
    //public enum Areas {PERIODISTA, ACTIVISTA, FILÓSOFA, ESCRITORA, DIRECTORADECINE, POETA, ACTRIZ,COMPOSITORA, ILUSTRADORA, BIOQUÍMICA, GENETISTA, FÍSICA};
    
    private final Integer id;
    
    private String nombre;
    private String apellidos;
    private String alias;
    private Date birthday;
    private Integer premiosRecibidos;
    private String paisDeResidencia;
    private String areaDeTrabajo;
    
    public Autora(Integer id, String nombre, String apellidos, String alias, Date birthday, Integer premiosRecibidos, String paisDeResidencia, String areaDeTrabajo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.alias = alias;
        this.birthday = birthday;
        this.premiosRecibidos = premiosRecibidos;
        this.paisDeResidencia = paisDeResidencia;
        this.areaDeTrabajo = areaDeTrabajo;
    }

    public Autora(Integer id, String alias) {
        this.id = id;
        this.alias = alias;
    }
    public abstract void crea();

    @Override
    public String toString() {
        return "Autora{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", alias=" + alias + ", birthday=" + birthday + ", premiosRecibidos=" + premiosRecibidos + ", paisDeResidencia=" + paisDeResidencia + ", areaDeTrabajo=" + areaDeTrabajo + '}';
    }
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getPremiosRecibidos() {
        return premiosRecibidos;
    }

    public void setPremiosRecibidos(Integer premiosRecibidos) {
        this.premiosRecibidos = premiosRecibidos;
    }

    public String getPaisDeResidencia() {
        return paisDeResidencia;
    }

    public void setPaisDeResidencia(String paisDeResidencia) {
        this.paisDeResidencia = paisDeResidencia;
    }

    public String getAreaDeTrabajo() {
        return areaDeTrabajo;
    }

    public void setAreaDeTrabajo(String areaDeTrabajo) {
        this.areaDeTrabajo = areaDeTrabajo;
    }

    
}
