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
    private String address;
    
    @NotNull(message = "Contact person can not be empty")
    private String contactPerson;
    
    @NotNull(message = "Company Name can not be empty")
    private String companyName;

    public Client(String username, String password, String address, String contactPerson, String companyName) {
        super(username, password);
        this.address = address;
        this.contactPerson = contactPerson;
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
