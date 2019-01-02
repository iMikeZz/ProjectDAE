/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author migue
 */
@XmlRootElement(name = "Configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationDTO implements Serializable{
    private int id;
    private String description;
    private int softwareCode;
    private String softwareName;
    private String state;
    private String clientUsername;
    private String contractData;
    private List<ExtensionDTO> extensions;
    private List<LicenseDTO> licenses;
    private List<ModuleDTO> modules;
    private List<ServiceDTO> services;
    private List<ParameterDTO> parameters;
    private List<RepositoryDTO> repositories;
    private List<MaterialDTO> materials;

    public ConfigurationDTO() {
        licenses = new LinkedList<>();
        modules = new LinkedList<>();
        parameters = new LinkedList<>();
        services = new LinkedList<>();
        repositories = new LinkedList<>();
        materials = new LinkedList<>();
        extensions = new LinkedList<>();
    }

    public ConfigurationDTO(int id, String description, int softwareCode, String softwareName, String state, String clientUsername, String contractData) {
        this.id = id;
        this.description = description;
        this.softwareCode = softwareCode;
        this.softwareName = softwareName;
        this.state = state;
        this.clientUsername = clientUsername;
        this.contractData = contractData;
        
        licenses = new LinkedList<>();
        modules = new LinkedList<>();
        parameters = new LinkedList<>();
        services = new LinkedList<>();
        repositories = new LinkedList<>();
        materials = new LinkedList<>();
        extensions = new LinkedList<>();
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getContractData() {
        return contractData;
    }

    public void setContractData(String contractData) {
        this.contractData = contractData;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void reset(){
        setId(0);
        setDescription(null);
        setSoftwareCode(0);
        setSoftwareName(null);
        setState(null);
        
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

    public int getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(int softwareCode) {
        this.softwareCode = softwareCode;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public List<ExtensionDTO> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionDTO> extensions) {
        this.extensions = extensions;
    }

    public List<LicenseDTO> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<LicenseDTO> licenses) {
        this.licenses = licenses;
    }

    public List<ModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDTO> modules) {
        this.modules = modules;
    }

    public List<ServiceDTO> getServices() {
        return services;
    }

    public void setServices(List<ServiceDTO> services) {
        this.services = services;
    }

    public List<ParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterDTO> parameters) {
        this.parameters = parameters;
    }

    public List<RepositoryDTO> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDTO> repositories) {
        this.repositories = repositories;
    }

    public List<MaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDTO> materials) {
        this.materials = materials;
    }
    
        public void addExtension(ExtensionDTO extension){
        this.extensions.add(extension);
    }
    
    public void removeExtension(ExtensionDTO extension){
        this.extensions.remove(extension);
    }
    
    public void addLicense(LicenseDTO license){
        this.licenses.add(license);
    }
    
    public void removeLicense(LicenseDTO license){
        this.licenses.remove(license);
    }
    
    public void addModule(ModuleDTO module){
        this.modules.add(module);
    }
    
    public void removeModule(ModuleDTO module){
        this.modules.remove(module);
    }
    
    public void addParameter(ParameterDTO parameter){
        this.parameters.add(parameter);
    }
    
    public void removeParameter(ParameterDTO parameter){
        this.parameters.remove(parameter);
    }
    
    public void addService(ServiceDTO service){
        this.services.add(service);
    }
    
    public void removeService(ServiceDTO service){
        this.services.remove(service);
    }
    
    public void addRepository(RepositoryDTO repository){
        this.repositories.add(repository);
    }
    
    public void removeRepository(RepositoryDTO repository){
        this.repositories.remove(repository);
    }
    
    public void addMaterial(MaterialDTO material){
        this.materials.add(material);
    }
    
    public void removeMaterial(MaterialDTO material){
        this.materials.remove(material);
    }
}
