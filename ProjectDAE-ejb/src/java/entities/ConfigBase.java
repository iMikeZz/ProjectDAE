/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author josea
 */
@Entity
public class ConfigBase implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
          
    @NotNull  
    private String description;
    
    @NotNull
    private Software software;
    
    @NotNull
    private List<License> licenses;
        
    @NotNull
    private List<Module> modules;
        
    @NotNull
    private List<Parameter> parameters;
        
    @NotNull
    private List<Service> services;
        
    @NotNull
    private List<Repository> repositories;
    
    @NotNull
    private List<Material> materials;
    
    @NotNull
    private List<Extension> extensions;
    
    public ConfigBase() {
        licenses = new LinkedList<>();
        modules = new LinkedList<>();
        parameters = new LinkedList<>();
        services = new LinkedList<>();
        repositories = new LinkedList<>();
        materials = new LinkedList<>();
    }

    public ConfigBase(int id, String description, Software software) {
        this.id = id;
        this.description = description;
        this.software = software;
        
        licenses = new LinkedList<>();
        modules = new LinkedList<>();
        parameters = new LinkedList<>();
        services = new LinkedList<>();
        repositories = new LinkedList<>();
        materials = new LinkedList<>();
        extensions = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
    
    
}
