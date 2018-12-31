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
@Table(name = "SERVICES")
@NamedQueries({
    @NamedQuery(
            name = "getAllServices",
            query = "SELECT t FROM Service t WHERE t.config IS NULL ORDER BY t.id"
    )
})
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String service;
    
    @ManyToOne
    @JoinColumn(name = "CONFIG_ID")
    private ConfigBase config;
    
    public Service() {
    }

    public Service(int id, String service, ConfigBase config) {
        this.id = id;
        this.service = service;
        this.config = config;
    }

    public Service(int id, String service) {
        this.id = id;
        this.service = service;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ConfigBase getConfig() {
        return config;
    }

    public void setConfig(ConfigBase config) {
        this.config = config;
    }
    
}
