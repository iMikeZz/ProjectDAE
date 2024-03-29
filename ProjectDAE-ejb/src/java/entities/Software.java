/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author josea
 */
@Entity
@Table(name = "SOFTWARES")
@NamedQueries({
    @NamedQuery(
            name = "getAllSoftwares",
            query = "SELECT t FROM Software t ORDER BY t.id"
    )
})
public class Software implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String version;
    
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<ConfigBase> configs;
    
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Extension> extensions;
    
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<License> licenses;
    
    @OneToMany(mappedBy = "software", cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    private List<Module> modules;
    
    public Software() {
        this.configs = new ArrayList<>();
        this.licenses = new ArrayList<>();
        this.extensions = new ArrayList<>();
        this.modules = new ArrayList<>();
    }

    public Software(int id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
        
        this.configs = new ArrayList<>();
        this.licenses = new ArrayList<>();
        this.extensions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ConfigBase> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigBase> configs) {
        this.configs = configs;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    
    public void addExtension(Extension extension){
        this.extensions.add(extension);
    }
    
    public void removeExtension(Extension extension){
        this.extensions.remove(extension);
    }
    
    public void addLicense(License license){
        this.licenses.add(license);
    }
    
    public void removeLicense(License license){
        this.licenses.remove(license);
    }
    
    public void addConfig(ConfigBase configBase){
        this.configs.add(configBase);
    }
    
    public void removeConfig(ConfigBase configBase){
        this.configs.remove(configBase);
    }
    
    public void addModule(Module module){
        this.modules.add(module);
    }
    
    public void removeModule(Module module){
        this.modules.remove(module);
    }
}
