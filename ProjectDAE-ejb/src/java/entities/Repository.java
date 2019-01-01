/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author josea
 */
@Entity
@Table(name = "REPOSITORIES")
@NamedQueries({
    @NamedQuery(
            name = "getAllRepositories",
            query = "SELECT t FROM Repository t WHERE t.config IS NULL ORDER BY t.id"
    )
})
public class Repository implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String link;
    
    @ManyToOne
    @JoinColumn(name = "CONFIG_ID")
    private ConfigBase config;
    
    public Repository() {
    }

    public Repository(int id, String link, ConfigBase config) {
        this.id = id;
        this.link = link;
        this.config = config;
    }

    public Repository(int id, String link) {
        this.id = id;
        this.link = link;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ConfigBase getConfig() {
        return config;
    }

    public void setConfig(ConfigBase config) {
        this.config = config;
    }
}
