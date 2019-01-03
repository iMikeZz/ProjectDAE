/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @ManyToOne
    @JoinColumn(name = "SOFTWARE_ID")
    private Software software;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGS_LICENSES",
            joinColumns = @JoinColumn(name = "CONFIG_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "LICENSE_ID", referencedColumnName = "ID"))
    private List<License> licenses;
        
    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGS_MODULES",
            joinColumns = @JoinColumn(name = "CONFIG_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID"))
    private List<Module> modules;
        
    @NotNull
    @OneToMany(mappedBy = "config", cascade = CascadeType.REMOVE)
    private List<Parameter> parameters;
        
    @NotNull
    @OneToMany(mappedBy = "config", cascade = CascadeType.REMOVE)
    private List<Service> services;
        
    @NotNull
    @OneToMany(mappedBy = "config", cascade = CascadeType.REMOVE)
    private List<Repository> repositories;
    
    @NotNull
    @OneToMany(mappedBy = "config", cascade = CascadeType.REMOVE)
    private List<Material> materials;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "CONFIGS_EXTENSIONS",
            joinColumns = @JoinColumn(name = "CONFIG_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "EXTENSION_ID", referencedColumnName = "ID"))
    private List<Extension> extensions;
    
    public ConfigBase() {
        licenses = new LinkedList<>();
        modules = new LinkedList<>();
        parameters = new LinkedList<>();
        services = new LinkedList<>();
        repositories = new LinkedList<>();
        materials = new LinkedList<>();
        extensions = new LinkedList<>();
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
    
    public void addModule(Module module){
        this.modules.add(module);
    }
    
    public void removeModule(Module module){
        this.modules.remove(module);
    }
    
    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }
    
    public void removeParameter(Parameter parameter){
        this.parameters.remove(parameter);
    }
    
    public void addService(Service service){
        this.services.add(service);
    }
    
    public void removeService(Service service){
        this.services.remove(service);
    }
    
    public void addRepository(Repository repository){
        this.repositories.add(repository);
    }
    
    public void removeRepository(Repository repository){
        this.repositories.remove(repository);
    }
    
    public void addMaterial(Material material){
        this.materials.add(material);
    }
    
    public void removeMaterial(Material material){
        this.materials.remove(material);
    }
}
