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
@Table(name = "LICENSES")
@NamedQueries({
    @NamedQuery(
            name = "getAllLicenses",
            query = "SELECT t FROM License t ORDER BY t.id"
    )
})
public class License implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String license;
    
    @ManyToMany(mappedBy = "licenses")
    private LinkedList<ConfigBase> configs;
    
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_ID")
    private Software software;

    public License() {
        this.configs = new LinkedList<>();
    }
    
    public License(int id, String license, Software software) {
        this.id = id;
        this.license = license;
        this.software = software;
        
        this.configs = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
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
    
    public void addConfig(ConfigBase configBase){
        this.configs.add(configBase);
    }
    
    public void removeConfig(ConfigBase configBase){
        this.configs.remove(configBase);
    }
}
