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
    ),
    @NamedQuery(
            name = "getAllAdministratorsByName",
            query = "SELECT t FROM Administrator t WHERE UPPER(t.name) LIKE UPPER(:name) ORDER BY t.name"
    ),
    @NamedQuery(
            name = "getAllAdministratorsOrderedByUsername",
            query = "SELECT t FROM Administrator t ORDER BY t.username"
    )
})
public class Administrator extends User implements Serializable{
    
    @NotNull(message = "Role can not be empty")
    private String role; //se der problema mudar 
    
    public Administrator() {
    }

    public Administrator(String username, String password, String name, String role, String email) {
        super(username, password, name, email, UserGroup.GROUP.Administrator);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
