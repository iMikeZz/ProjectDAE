/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import web.*;
import dtos.RepositoryDTO;
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
import util.URILookup;

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
    
    private String creationPage;
  
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
            return client.target(URILookup.getBaseAPI())
                    .path("/repositories/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<RepositoryDTO> getAllConfigurationRepositories(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/repositories/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration repositories in method getAllConfigurationRepositories", logger);
            return null;
        }
    }
   
    public List<RepositoryDTO> getAllRepositories(){
        try {
            return client.target(URILookup.getBaseAPI())
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
            if (manager.getCurrentConfiguration()!= null) {
                newRepository.setConfig_id(manager.getCurrentConfiguration().getId());
            }
            client.target(URILookup.getBaseAPI())
                    .path("repositories/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newRepository));
            newRepository.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        
        if (creationPage.equals("newTemplate")){
            return "/admin/templates/admin_template_create?faces-redirect=true";
        }
        if (creationPage.equals("newConfiguration")){
            return "/admin/configurations/admin_configuration_create?faces-redirect=true";
        }
        if (manager.getCurrentConfiguration() != null){
            return "/admin/configurations/admin_configuration_update?faces-redirect=true";
        }
        if (manager.getCurrentTemplate() != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return null;
    }
    
    public void newRepositoryRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newRepository");
            creationPage = param.getValue().toString();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String addRepository(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addRepositoryId");
            int repository_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
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
            client.target(URILookup.getBaseAPI())
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
    
    public List<RepositoryDTO> getRepositoriesNotInTemplate(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/repositories/repositoriesNotInTemplate/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getRepositoriesNotInTemplate", logger);
            return null;
        }
    }
    
    //*****************CONFIGURATIONS**************************
    public String createRepositoryConfiguration() {
        try {
            if (manager.getCurrentConfiguration() != null) {
                newRepository.setConfig_id(manager.getCurrentConfiguration().getId());
            }
            client.target(URILookup.getBaseAPI())
                    .path("repositories/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newRepository));
            newRepository.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (manager.getCurrentConfiguration() != null)
            return "/admin/configurations/admin_configuration_update?faces-redirect=true";
        
        return "/admin/configurations/admin_configuration_create?faces-redirect=true";
    }
    
    public String addRepositoryConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addRepositoryId");
            int repository_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/repositories/addToConfiguration/" + repository_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding repository in method addRepositoryConfiguration", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeRepositoryConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeRepositoryId");
            int repository_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/repositories/removeFromConfiguration/" + repository_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing repository in method removeRepositoryConfiguration", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<RepositoryDTO> getRepositoriesNotInConfiguration(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/repositories/repositoriesNotInConfiguration/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getRepositoriesNotInConfiguration", logger);
            return null;
        }
    }
}
