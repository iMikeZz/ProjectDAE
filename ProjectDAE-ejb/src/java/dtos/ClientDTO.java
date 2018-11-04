/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;


/**
 *
 * @author Ruben Lauro
 */

public class ClientDTO extends UserDTO implements Serializable {
    
    private String address;
    private String contactPerson;
    private String companyName;

    public ClientDTO() {
    }

    public ClientDTO(String username, String password, String address, String contactPerson, String companyName) {
        super(username, password);
        this.address = address;
        this.contactPerson = contactPerson;
        this.companyName = companyName;
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
        setAddress(null);
        setContactPerson(null);
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
