/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import dtos.ClientDTO;
import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.LicenseDTO;
import dtos.MaterialDTO;
import dtos.ModuleDTO;
import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import dtos.ServiceDTO;
import dtos.SoftwareDTO;
import dtos.TemplateDTO;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import util.URILookup;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "configurationManager")
@SessionScoped
public class ConfigurationManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ConfigurationManager.class.getName());
    
    private static final String SEARCHBYDESCRIPTION = "SEARCHBYDESCRIPTION";
    private static final String ALLTEMPLATES = "ALLTEMPLATES";
    private static final String SORTTEMPLATESBYID = "SORTTEMPLATESBYID";
    private static final String SORTTEMPLATESBYDESCRIPTION = "SORTTEMPLATESBYDESCRIPTION";
    
   
    private static final String ALLCONFIGURATIONS = "ALLCONFIGURATIONS";
    
    private String templatesVersion = ALLTEMPLATES;
    private String searchValue;
    
    private TemplateDTO newTemplate;
    private ConfigurationDTO newConfiguration;
    
    private TemplateDTO TemplateChoosed;
    
    private String configurationsVersion = ALLCONFIGURATIONS;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    public ConfigurationManager() {
        this.newTemplate = new TemplateDTO();
        this.newConfiguration = new ConfigurationDTO();
    }
   
    public String getTemplatesVersion() {
        return templatesVersion;
    }
    
    public void setTemplatesVersion(String templatesVersion) {
        this.templatesVersion = templatesVersion;
    }
    
    public String getSearchValue() {
        return searchValue;
    }
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    public String getSEARCHBYDESCRIPTION() {
        return SEARCHBYDESCRIPTION;
    }
    
    public String getALLTEMPLATES() {
        return ALLTEMPLATES;
    }
    
    public String getALLCONFIGURATIONS() {
        return ALLCONFIGURATIONS;
    }
    
    public ConfigurationDTO getNewConfiguration() {
        return newConfiguration;
    }

    public void setnewConfiguration(ConfigurationDTO newConfiguration) {
        this.newConfiguration = newConfiguration;
    }

    public String getConfigurationsVersion() {
        return configurationsVersion;
    }

    public void setConfigurationsVersion(String configurationsVersion) {
        this.configurationsVersion = configurationsVersion;
    }
    
    public TemplateDTO getNewTemplate() {
        return newTemplate;
    }

    public void setNewTemplate(TemplateDTO newTemplate) {
        this.newTemplate = newTemplate;
    }

        
    public TemplateDTO getTemplateChoosed() {
        return TemplateChoosed;
    }

    public void setTemplateChoosed(TemplateDTO TemplateChoosed) {
        this.TemplateChoosed = TemplateChoosed;
    }
  
    public String getSORTTEMPLATESBYID() {
        return SORTTEMPLATESBYID;
    }

    public String getSORTTEMPLATESBYDESCRIPTION() {
        return SORTTEMPLATESBYDESCRIPTION;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
   
    //*******************TEMPLATES********************************
    public List<TemplateDTO> getAllTemplates(){
        try {
            switch (templatesVersion) {
                case ALLTEMPLATES:
                    return getTemplatesListByUrl("/templates/all");
                case SEARCHBYDESCRIPTION:
                    if (!searchValue.equals("")) {
                        return getTemplatesListByUrl("/templates/" + searchValue);
                    }
                    return getTemplatesListByUrl("/templates/all");
                case SORTTEMPLATESBYDESCRIPTION:
                    return getTemplatesListByUrl("/templates/allOrderedByDescription");
                case SORTTEMPLATESBYID:
                    return getTemplatesListByUrl("/templates/all");
                default:
                    return null;
            }
            
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplates", logger);
            return null;
        }
    }
    
    public List<SoftwareDTO> getAllSoftwares(){
        try {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            return manager.getClient().target(URILookup.getBaseAPI())
                    .path("/softwares/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllSoftwares", logger);
            return null;
        }
    }
    
        
    public String createTemplate() {
        try {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("templates/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newTemplate));
            newTemplate.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public String cancelCreateTemplate() {
        newTemplate.reset();
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public String cancelCurrentTemplateDetailsAndUpdate() {
        if (manager.getUserManager().getUsername() == null && manager.getUserManager().getPassword() == null) {
            return "/index?faces-redirect=true";
        }
        manager.setCurrentTemplate(null);
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public String cancelCurrentConfigurationDetailsAndUpdate() {
        manager.setCurrentConfiguration(null);
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public String createConfiguration() {
        try {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("configurations/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newConfiguration));
            newConfiguration.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public void createConfigurationWithTemplate(ActionEvent event) {
        TemplateDTO templateDTOFromDatabase = null;
        newConfiguration.reset();
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("createConfigurationTemplate");
            TemplateDTO template = (TemplateDTO)param.getValue();
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            templateDTOFromDatabase = manager.getClient().target(URILookup.getBaseAPI())
                    .path("/templates/template/" + template.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(TemplateDTO.class);
            newConfiguration.setSoftwareCode(templateDTOFromDatabase.getSoftwareCode());
            newConfiguration.setExtensions(templateDTOFromDatabase.getExtensions());
            newConfiguration.setLicenses(templateDTOFromDatabase.getLicenses());
            newConfiguration.setMaterials(templateDTOFromDatabase.getMaterials());
            newConfiguration.setModules(templateDTOFromDatabase.getModules());
            newConfiguration.setParameters(templateDTOFromDatabase.getParameters());
            newConfiguration.setRepositories(templateDTOFromDatabase.getRepositories());
            newConfiguration.setServices(templateDTOFromDatabase.getServices());
                        
            //newConfiguration.setSoftwareCode(template.getSoftwareCode());
            manager.setCurrentSoftwareId(template.getSoftwareCode());
            
            manager.setCurrentTemplate(template);
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String copyConfiguration() {
        try {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("configurations/create/" + manager.getCurrentClientUsername())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(manager.getCurrentConfiguration()));
        }
        
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public String updateTemplate(){
        try {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("templates/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
            manager.setCurrentTemplate(null);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating template in method updateTemplate", logger);
            return null;
        }
        return "/admin/admin_index?facelet-redirect=true";
    }
    
     public String updateConfiguration(){
        try {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("configurations/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
            manager.setCurrentConfiguration(null);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating template in method updateConfiguration", logger);
            return null;
        }
        return "/admin/admin_index?facelet-redirect=true";
    }
    
    public void removeTemplate(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteTemplateId");
            int username = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/templates/" + username)
                    .request(MediaType.APPLICATION_XML)
                    .delete(Boolean.class);
            manager.setCurrentTemplate(null);
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing template in method removeTemplate ", logger);
        }
    }
    
    public void removeConfiguration(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteConfigurationId");
            int username = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/configurations/" + username)
                    .request(MediaType.APPLICATION_XML)
                    .delete(Boolean.class);
            manager.setCurrentConfiguration(null);
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing template in method removeConfiguration ", logger);
        }
    }
    
    //**************COSTUM METHODS
    private List<TemplateDTO> getTemplatesListByUrl(String url){
        if (userManager.getUsername() != null && userManager.getPassword() != null) {
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
        }
        return manager.getClient().target(URILookup.getBaseAPI())
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<TemplateDTO>>() {
                });
    } 
    
    private List<ConfigurationDTO> getConfigurationsListByUrl(String url){
        manager.clientRegister(userManager.getUsername(), userManager.getPassword());
        return manager.getClient().target(URILookup.getBaseAPI())
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ConfigurationDTO>>() {
                });
    }
    
    public void removeExtension(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeExtension");
        ExtensionDTO extensionDTO = (ExtensionDTO)param.getValue();
        if (newTemplate.getExtensions().contains(extensionDTO)) {
            newTemplate.removeExtension(extensionDTO);
        }
    }
    
    public void addExtension(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addExtension");
        ExtensionDTO extensionDTO = (ExtensionDTO)param.getValue();
        if (!newTemplate.getExtensions().contains(extensionDTO)) {
            newTemplate.addExtension(extensionDTO);
        }
    }
    
    public void removeLicense(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeLicense");
        LicenseDTO licenseDTO = (LicenseDTO)param.getValue();
        if (newTemplate.getLicenses().contains(licenseDTO)) {
            newTemplate.removeLicense(licenseDTO);
        }
    }
    
    public void addLicense(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addLicense");
        LicenseDTO licenseDTO = (LicenseDTO)param.getValue();
        if (!newTemplate.getLicenses().contains(licenseDTO)) {
            newTemplate.addLicense(licenseDTO);
        }
    }
    
    public void removeMaterial(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeMaterial");
        MaterialDTO materialDTO = (MaterialDTO)param.getValue();
        if (newTemplate.getMaterials().contains(materialDTO)) {
            newTemplate.removeMaterial(materialDTO);
        }
    }
    
    public void addMaterial(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addMaterial");
        MaterialDTO materialDTO = (MaterialDTO)param.getValue();
        if (!newTemplate.getMaterials().contains(materialDTO)) {
            newTemplate.addMaterial(materialDTO);
        }
    }
    
    public void removeModule(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeModule");
        ModuleDTO moduleDTO = (ModuleDTO)param.getValue();
        if (newTemplate.getModules().contains(moduleDTO)) {
            newTemplate.removeModule(moduleDTO);
        }
    }
    
    public void addModule(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addModule");
        ModuleDTO moduleDTO = (ModuleDTO)param.getValue();
        if (!newTemplate.getModules().contains(moduleDTO)) {
            newTemplate.addModule(moduleDTO);
        }
    }
    
    public void removeParamater(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeParamater");
        ParameterDTO parameterDTO = (ParameterDTO)param.getValue();
        if (newTemplate.getParameters().contains(parameterDTO)) {
            newTemplate.removeParameter(parameterDTO);
        }
    }
    
    public void addParamater(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addParamater");
        ParameterDTO parameterDTO = (ParameterDTO)param.getValue();
        if (!newTemplate.getParameters().contains(parameterDTO)) {
            newTemplate.addParameter(parameterDTO);
        }
    }
    
    public void removeRepository(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeRepository");
        RepositoryDTO repositoryDTO = (RepositoryDTO)param.getValue();
        if (newTemplate.getRepositories().contains(repositoryDTO)) {
            newTemplate.removeRepository(repositoryDTO);
        }
    }
    
    public void addRepository(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addRepository");
         RepositoryDTO repositoryDTO = (RepositoryDTO)param.getValue();
        if (!newTemplate.getRepositories().contains(repositoryDTO)) {
            newTemplate.addRepository(repositoryDTO);
        }
    }
    
    public void removeService(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeService");
        ServiceDTO serviceDTO = (ServiceDTO)param.getValue();
        if (newTemplate.getServices().contains(serviceDTO)) {
            newTemplate.removeService(serviceDTO);
        }
    }
    
    public void addService(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addService");
        ServiceDTO serviceDTO = (ServiceDTO)param.getValue();
        if (!newTemplate.getServices().contains(serviceDTO)) {
            newTemplate.addService(serviceDTO);
        }
    }
    
    //*************CONFIGURATIONS*********************
    public void removeExtensionConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeExtension");
        ExtensionDTO extensionDTO = (ExtensionDTO)param.getValue();
        if (newConfiguration.getExtensions().contains(extensionDTO)) {
            newConfiguration.removeExtension(extensionDTO);
        }
    }
    
    public void addExtensionConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addExtension");
        ExtensionDTO extensionDTO = (ExtensionDTO)param.getValue();
        if (!newConfiguration.getExtensions().contains(extensionDTO)) {
            newConfiguration.addExtension(extensionDTO);
        }
    }
    
    public void removeLicenseConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeLicense");
        LicenseDTO licenseDTO = (LicenseDTO)param.getValue();
        if (newConfiguration.getLicenses().contains(licenseDTO)) {
            newConfiguration.removeLicense(licenseDTO);
        }
    }
    
    public void addLicenseConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addLicense");
        LicenseDTO licenseDTO = (LicenseDTO)param.getValue();
        if (!newConfiguration.getLicenses().contains(licenseDTO)) {
            newConfiguration.addLicense(licenseDTO);
        }
    }
    
    public void removeMaterialConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeMaterial");
        MaterialDTO materialDTO = (MaterialDTO)param.getValue();
        if (newConfiguration.getMaterials().contains(materialDTO)) {
            newConfiguration.removeMaterial(materialDTO);
        }
    }
    
    public void addMaterialConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addMaterial");
        MaterialDTO materialDTO = (MaterialDTO)param.getValue();
        if (!newConfiguration.getMaterials().contains(materialDTO)) {
            newConfiguration.addMaterial(materialDTO);
        }
    }
    
    public void removeModuleConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeModule");
        ModuleDTO moduleDTO = (ModuleDTO)param.getValue();
        if (newConfiguration.getModules().contains(moduleDTO)) {
            newConfiguration.removeModule(moduleDTO);
        }
    }
    
    public void addModuleConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addModule");
        ModuleDTO moduleDTO = (ModuleDTO)param.getValue();
        if (!newConfiguration.getModules().contains(moduleDTO)) {
            newConfiguration.addModule(moduleDTO);
        }
    }
    
    public void removeParamaterConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeParamater");
        ParameterDTO parameterDTO = (ParameterDTO)param.getValue();
        if (newConfiguration.getParameters().contains(parameterDTO)) {
            newConfiguration.removeParameter(parameterDTO);
        }
    }
    
    public void addParamaterConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addParamater");
        ParameterDTO parameterDTO = (ParameterDTO)param.getValue();
        if (!newConfiguration.getParameters().contains(parameterDTO)) {
            newConfiguration.addParameter(parameterDTO);
        }
    }
    
    public void removeRepositoryConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeRepository");
        RepositoryDTO repositoryDTO = (RepositoryDTO)param.getValue();
        if (newConfiguration.getRepositories().contains(repositoryDTO)) {
            newConfiguration.removeRepository(repositoryDTO);
        }
    }
    
    public void addRepositoryConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addRepository");
         RepositoryDTO repositoryDTO = (RepositoryDTO)param.getValue();
        if (!newConfiguration.getRepositories().contains(repositoryDTO)) {
            newConfiguration.addRepository(repositoryDTO);
        }
    }
    
    public void removeServiceConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("removeService");
        ServiceDTO serviceDTO = (ServiceDTO)param.getValue();
        if (newConfiguration.getServices().contains(serviceDTO)) {
            newConfiguration.removeService(serviceDTO);
        }
    }
    
    public void addServiceConfiguration(ActionEvent event){
        UIParameter param = (UIParameter) event.getComponent().findComponent("addService");
        ServiceDTO serviceDTO = (ServiceDTO)param.getValue();
        if (!newConfiguration.getServices().contains(serviceDTO)) {
            newConfiguration.addService(serviceDTO);
        }
    }
    
    public String cancelCreateConfiguration() {
        newConfiguration.reset();
        return "/admin/admin_index?faces-redirect=true";
    }
    
    //*************CONFIGURATIONS*********************
        public List<ConfigurationDTO> getAllConfigurations(){
        try {
            switch (configurationsVersion) {
                case ALLCONFIGURATIONS:
                    return getConfigurationsListByUrl("/configurations/all");
                case SEARCHBYDESCRIPTION:
                    if (!searchValue.equals("")) {
                        return getConfigurationsListByUrl("/configurations/" + searchValue);
                    }
                    return getConfigurationsListByUrl("/configurations/all");
                default:
                    return null;
            }
            
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllConfigurations", logger);
            return null;
        }
    }    
}
