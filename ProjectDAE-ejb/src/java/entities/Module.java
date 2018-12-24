/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author josea
 */
@Entity
@Table(name = "MODULES")
@NamedQueries({
    @NamedQuery(
            name = "getAllModules",
            query = "SELECT t FROM Module t ORDER BY t.id"
    )
})
public class Module implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    
    @ManyToMany(mappedBy = "modules")
    private LinkedList<ConfigBase> configs;
    
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_ID")
    private Software software;

    public Module() {
        this.configs = new LinkedList<>();
    }

    public Module(int id, String description, Software software) {
        this.id = id;
        this.description = description;
        this.software = software;
        
        this.configs = new LinkedList<>();
    }
    
    public LinkedList<ConfigBase> getConfigs() {
        return configs;
    }

    public void setConfigs(LinkedList<ConfigBase> configs) {
        this.configs = configs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }
}
