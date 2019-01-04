/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import dtos.ConfigurationDTO;
import web.*;
import dtos.ExtensionDTO;
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
@ManagedBean(name = "extensionManager")
@SessionScoped
public class ExtensionManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ExtensionManager.class.getName());
    
    private ExtensionDTO newExtension;
    
    private String creationPage;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    public ExtensionManager() {
        this.newExtension = new ExtensionDTO();
    }
    
    public ExtensionDTO getNewExtension() {
        return newExtension;
    }
    
    public void setNewExtension(ExtensionDTO newExtension) {
        this.newExtension = newExtension;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
        
    //*******************TEMPLATES********************************
    public List<ExtensionDTO> getAllTemplateExtensions(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/extensions/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplateExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllConfigurationExtensions(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/extensions/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration extensions in method allConfigurationExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllExtensions(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/extensions/all/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all extensions in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getExtensionsNotInTemplate(){
        try {
            manager.setCurrentSoftwareId(manager.getCurrentTemplate().getSoftwareCode());
            return client.target(URILookup.getBaseAPI())
                    .path("/extensions/extensionsNotInTemplate/" + manager.getCurrentTemplate().getId() + "/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public String createExtension() {
        try {
            newExtension.setSoftware_id(manager.getCurrentSoftwareId());
            if (manager.getCurrentTemplate() != null){
              client.target(URILookup.getBaseAPI())
                    .path("extensions/create/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newExtension));
                newExtension.reset();  
            } else if(manager.getCurrentConfiguration() != null){
                client.target(baseUri)
                    .path("extensions/create/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newExtension));
                newExtension.reset();  
            } else{
                client.target(URILookup.getBaseAPI())
                    .path("extensions/create/" + 0)
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newExtension));
                newExtension.reset();
            }
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
        if (manager.getCurrentTemplate() != null){
            return "/admin/templates/admin_template_update?faces-redirect=true";
        }
        return null;
    }
    
    public void newExtensionRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newExtension");
            creationPage = param.getValue().toString();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String addExtension(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addExtensionId");
            int extension_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/extensions/addToTemplate/"+ extension_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding extension in method addExtension ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeExtension(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeExtensionId");
            int extension_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/extensions/removeFromTemplate/"+ extension_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing extension in method removeExtension ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
    }
    
    //************CONFIGURATIONS******************
    public List<ExtensionDTO> getExtensionsNotInConfiguration(){
        try {
            manager.setCurrentSoftwareId(manager.getCurrentConfiguration().getSoftwareCode());
            return client.target(URILookup.getBaseAPI())
                    .path("/extensions/extensionsNotInConfiguration/" + manager.getCurrentConfiguration().getId() + "/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
        
    public String addExtensionConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addExtensionId");
            int extension_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/extensions/addToConfiguration/"+ extension_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding extension in method addExtension ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeExtensionConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeExtensionId");
            int extension_id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/extensions/removeFromConfiguration/"+ extension_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing extension in method removeExtension ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
    }
}
