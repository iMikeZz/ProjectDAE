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
    
    @NotNull(message = "Address can not be empty")
    protected String address;
    
    @NotNull(message = "Contact person can not be empty")
    protected String contactPerson;

    public Client(String username, String password, String name, String address, String contactPerson) {
        super(username, password, name);
        this.address = address;
        this.contactPerson = contactPerson;
    }

    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
}
