/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ordinario;

import java.time.LocalDate;

/**
 *
 * @author cristina
 */
public class Escritora extends Autora {

    public Escritora(Integer id, String nombre, String apellidos, String alias, LocalDate birthday, Integer premiosRecibidos, String paisDeResidencia, String areaDeTrabajo) {
        super(id, nombre, apellidos, alias, birthday, premiosRecibidos, paisDeResidencia, areaDeTrabajo);
    }

    @Override
    public void crea() {
        System.out.println("Como soy "+this.getAreaDeTrabajo()+" también escribo");
    }
    
}
