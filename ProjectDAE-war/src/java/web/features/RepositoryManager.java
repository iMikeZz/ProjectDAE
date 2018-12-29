/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import web.*;
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
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "repositoryManager")
@SessionScoped
public class RepositoryManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(RepositoryManager.class.getName());

    private RepositoryDTO newRepository;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
   
  
    public RepositoryManager() {
        this.newRepository = new RepositoryDTO();
    }
    
    public RepositoryDTO getNewRepository() {
        return newRepository;
    }
    
    public void setNewRepository(RepositoryDTO newRepository) {
        this.newRepository = newRepository;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public List<RepositoryDTO> getAllTemplateRepositories(){
        try {
            return client.target(baseUri)
                    .path("/repositories/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
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
    
    public String createRepository() {
        try {
            if (manager.getCurrentTemplate() != null) {
                newRepository.setConfig_id(manager.getCurrentTemplate().getId());
            }
            client.target(baseUri)
                    .path("repositories/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newRepository));
            newRepository.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (manager.getCurrentTemplate() != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String addRepository(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addRepositoryId");
            int repository_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/repositories/addToTemplate/" + repository_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding repository in method addRepository", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeRepository(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeRepositoryId");
            int repository_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/repositories/removeFromTemplate/" + repository_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing repository in method removeRepository", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
}