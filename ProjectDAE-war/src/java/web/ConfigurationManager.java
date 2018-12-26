/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import dtos.ClientDTO;
import dtos.ExtensionDTO;
import dtos.LicenseDTO;
import dtos.MaterialDTO;
import dtos.ModuleDTO;
import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import dtos.ServiceDTO;
import dtos.SoftwareDTO;
import dtos.TemplateDTO;
import ejbs.TemplateBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    
    private static final String SEARCHBYDESCRIPTION = "SEARCHCLIENTSBYNAME";
    private static final String ALLTEMPLATES = "ALLTEMPLATES";
    
    @EJB
    private TemplateBean templateBean;
    private TemplateDTO currentTemplate;
    private TemplateDTO newTemplate;
    
    private String templatesVersion = ALLTEMPLATES;
    private String searchValue;
    
    public ConfigurationManager() {
        this.newTemplate = new TemplateDTO();
    }
    
    public TemplateBean getTemplateBean() {
        return templateBean;
    }
    
    public void setTemplateBean(TemplateBean templateBean) {
        this.templateBean = templateBean;
    }
    
    public TemplateDTO getCurrentTemplate() {
        return currentTemplate;
    }
    
    public void setCurrentTemplate(TemplateDTO currentTemplate) {
        this.currentTemplate = currentTemplate;
    }
    
    public TemplateDTO getNewTemplate() {
        return newTemplate;
    }
    
    public void setNewTemplate(TemplateDTO newTemplate) {
        this.newTemplate = newTemplate;
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
    
    public List<ExtensionDTO> getAllExtensions(){
        try {
            return client.target(baseUri)
                    .path("/extensions/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllTemplateExtensions(){
        try {
            return client.target(baseUri)
                    .path("/extensions/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllTemplateLicenses(){
        try {
            return client.target(baseUri)
                    .path("/licenses/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllTemplateMaterials(){
        try {
            return client.target(baseUri)
                    .path("/materials/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllTemplateModules(){
        try {
            return client.target(baseUri)
                    .path("/modules/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<LicenseDTO> getAllLicenses(){
        try {
            return client.target(baseUri)
                    .path("/licenses/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllLicenses", logger);
            return null;
        }
    }
    
    public List<MaterialDTO> getAllMaterials(){
        try {
            return client.target(baseUri)
                    .path("/materials/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllMaterials", logger);
            return null;
        }
    }
    
    public List<ModuleDTO> getAllModules(){
        try {
            return client.target(baseUri)
                    .path("/modules/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ModuleDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllModules", logger);
            return null;
        }
    }
    
    public List<ParameterDTO> getAllParameters(){
        try {
            return client.target(baseUri)
                    .path("/parameters/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllParameters", logger);
            return null;
        }
    }
    
    public List<RepositoryDTO> getAllRepositories(){
        try {
            return client.target(baseUri)
                    .path("/repositories/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllRepositories", logger);
            return null;
        }
    }
    
    public List<ServiceDTO> getAllServices(){
        try {
            return client.target(baseUri)
                    .path("/services/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllServices", logger);
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
            System.out.println("passei aqui");
            client.target(baseUri)
                    .path("templates/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTemplate));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating client in method updateClient", logger);
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
            FacesExceptionHandler.handleException(e, "Problem removing student in method removeTemplate ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
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
