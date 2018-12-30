/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static web.Manager.baseUri;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "anonymousManager")
@SessionScoped
public class AnonymousManager implements Serializable {
            
    protected Client client;
        
    private static final Logger logger = Logger.getLogger(AnonymousManager.class.getName());
    
    private static final String SEARCHBYDESCRIPTION = "SEARCHCLIENTSBYNAME";
    private static final String ALLTEMPLATES = "ALLTEMPLATES";
   
    private String templatesVersion = ALLTEMPLATES;
    private String searchValue;
    
    private TemplateDTO newTemplate;

    public TemplateDTO getNewTemplate() {
        return newTemplate;
    }

    public void setNewTemplate(TemplateDTO newTemplate) {
        this.newTemplate = newTemplate;
    }
    
    public AnonymousManager() {
        this.newTemplate = new TemplateDTO();
        client = ClientBuilder.newClient();
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
    
    //**************COSTUM METHODS
    private List<TemplateDTO> getTemplatesListByUrl(String url){
        return client.target(baseUri)
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<TemplateDTO>>() {
                });
    }
}
