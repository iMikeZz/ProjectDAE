/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.roles;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Client extends User implements Serializable {
    
    @NotNull(message = "Morada não pode estar vazia")
    protected String address;
    
    @NotNull(message = "Pessoa de contacto não pode estar vazio")
    protected String contactPerson;

    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }
}
