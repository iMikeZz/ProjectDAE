/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.roles;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author migue
 */
@Entity
@Table(name = "ADMINISTRATORS")
@NamedQueries({
    @NamedQuery(
            name = "getAllAdministrators",
            query = "SELECT t FROM Administrator t ORDER BY t.name"
    )
})
public class Administrator implements Serializable{
    @Id
    protected String username;
     
    @NotNull(message = "Password não pode estar vazia")
    protected String password;

    @NotNull(message = "Nome não pode estar vazio")
    protected String name;
    
    @NotNull(message = "Email não pode estar vazio")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
            + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
    protected String email;
    
    @NotNull(message = "Cargo não pode estar vazio")
    protected String cargo;
}
