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
 * @author Ruben Lauro
 */
@Entity
@Table(name = "EXTENSIONS")
@NamedQueries({
    @NamedQuery(
            name = "getAllExtensions",
            query = "SELECT t FROM Extension t ORDER BY t.id"
    )
})
public class Extension implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String extension;
    
    @ManyToMany(mappedBy = "extensions")
    private LinkedList<ConfigBase> configs;
    
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_ID")
    private Software software;

    public Extension() {
        this.configs = new LinkedList<>();
    }

    public Extension(int id, String extension, Software software) {
        this.id = id;
        this.extension = extension;
        this.software = software;
        
        this.configs = new LinkedList<>();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<ConfigBase> getConfigs() {
        return configs;
    }

    public void setConfigs(LinkedList<ConfigBase> configs) {
        this.configs = configs;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }
}
