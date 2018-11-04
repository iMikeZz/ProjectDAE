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
            query = "SELECT t FROM Administrator t ORDER BY t.nome"
    )
})
public class Administrator extends User implements Serializable {
    
    @NotNull(message = "Email não pode estar vazio")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
            + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{invalid.email}")
    protected String email;
    
    @NotNull(message = "Cargo não pode estar vazio")
    protected String cargo;
}
