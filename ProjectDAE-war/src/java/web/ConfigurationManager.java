/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

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
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static web.Manager.baseUri;

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
   
    private static final String ALLCONFIGURATIONS = "ALLCONFIGURATIONS";
    
    private String templatesVersion = ALLTEMPLATES;
    private String searchValue;
    
    private TemplateDTO newTemplate;
    private ConfigurationDTO newConfiguration;
    
    private String configurationsVersion = ALLCONFIGURATIONS;

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
            return client.target(baseUri)
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
            client.target(baseUri)
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
    
    public String updateTemplate(){
        try {
            client.target(baseUri)
                    .path("templates/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(userManager.getClass()));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating template in method updateTemplate", logger);
            return null;
        }
        return "/admin/admin_index?facelet-redirect=true";
    }
    
    public String removeTemplate(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteTemplateId");
            int username = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/templates/" + username)
                    .request(MediaType.APPLICATION_XML)
                    .delete(Boolean.class);
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing template in method removeTemplate ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    //**************COSTUM METHODS
    private List<TemplateDTO> getTemplatesListByUrl(String url){
        return client.target(baseUri)
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<TemplateDTO>>() {
                });
    } 
    
    private List<ConfigurationDTO> getConfigurationsListByUrl(String url){
        return client.target(baseUri)
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ConfigurationDTO>>() {
                });
    }
    
    //*************CONFIGURATIONS*********************
        public List<ConfigurationDTO> getAllConfigurations(){
        try {
            System.out.println("ESTOU AQUI");
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
