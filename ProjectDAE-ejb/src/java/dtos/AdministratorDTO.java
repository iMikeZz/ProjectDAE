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

public class AdministratorDTO extends UserDTO implements Serializable {
    
    private String name;
    private String email;
    private String role;

    public AdministratorDTO() {
    }

    public AdministratorDTO(String username, String password, String name, String email, String role) {
        super(username, password);
        this.name = name;
        this.email = email;
        this.role = role;
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
        setEmail(null);
        setRole(null);
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
