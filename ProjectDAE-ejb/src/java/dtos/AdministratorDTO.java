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
@XmlRootElement(name = "Administrator")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdministratorDTO extends UserDTO implements Serializable {
    private String role;

    public AdministratorDTO() {
    }

    public AdministratorDTO(String username, String password, String name, String email, String role) {
        super(username, password, name, email);
        this.role = role;
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
        setRole(null);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
