/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.roles;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author migue
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "getAllAdministrators",
            query = "SELECT t FROM Administrator t ORDER BY t.name"
    )
})
public class Administrator extends User implements Serializable{
    
    @NotNull(message = "Email can not be empty")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
            + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
    private String email;
    
    @NotNull(message = "Role can not be empty")
    private String role; //se der problema mudar 
    
    @NotNull(message = "Name can not be empty")
    protected String name;

    public Administrator() {
    }

    public Administrator(String username, String password, String name, String email, String role) {
        super(username, password);
        this.name = name;
        this.email = email;
        this.role = role;
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
