/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Ruben Lauro
 */
@XmlRootElement(name = "Client")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientDTO extends UserDTO implements Serializable {
    
    private String address;
    private String contactPerson;

    public ClientDTO() {
    }

    public ClientDTO(String username, String password, String address, String contactPerson, String companyName) {
        super(username, password, companyName);
        this.address = address;
        this.contactPerson = contactPerson;
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
}
