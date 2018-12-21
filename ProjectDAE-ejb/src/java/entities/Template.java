/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
/**
 *
 * @author josea
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "getAllTemplates",
            query = "SELECT t FROM Template t ORDER BY t.id"
    )
})
public class Template extends ConfigBase implements Serializable {
    
    public Template() {
    }

    public Template(int id, String description, Software software) {
        super(id, description, software);
    }
}
