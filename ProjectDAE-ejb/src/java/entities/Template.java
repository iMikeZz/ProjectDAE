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
    ),
    @NamedQuery(
            name = "getAllTemplatesByDescription",
            query = "SELECT t FROM Template t WHERE UPPER(t.description) LIKE UPPER(:description) ORDER BY t.description"
    ),
    @NamedQuery(
            name = "getAllTemplatesOrderedByDescription",
            query = "SELECT t FROM Template t ORDER BY t.description"
    ),
})
public class Template extends ConfigBase implements Serializable {
    
    public Template() {
    }

    public Template(int id, String description, Software software) {
        super(id, description, software);
    }
}
