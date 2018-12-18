/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.roles.Client;
import entities.utils.State;
import java.io.Serializable;
import java.security.cert.Extension;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author josea
 */
@Entity
public class ConfigBase implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
          
    @ManyToOne
    @JoinColumn(name = "CLIENT_CODE")
    @NotNull(message = "A client is needed to this configuration")
    private Client client;
    
    @NotNull  
    private String description;
    
    @NotNull
    private String contractData;
    
    @NotNull
    private State state;
    
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

    public ConfigBase(int id, Client client, String description, String contractData, State state, Software software) {
        this.id = id;
        this.client = client;
        this.description = description;
        this.contractData = contractData;
        this.state = state;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContractData() {
        return contractData;
    }

    public void setContractData(String contractData) {
        this.contractData = contractData;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
