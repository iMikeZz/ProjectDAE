/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import web.*;
import dtos.ModuleDTO;
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

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "moduleManager")
@SessionScoped
public class ModuleManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ModuleManager.class.getName());
    
    private ModuleDTO newModule;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    public ModuleManager() {
        this.newModule = new ModuleDTO();
    }
    
    public ModuleDTO getNewModule() {
        return newModule;
    }
    
    public void setNewModule(ModuleDTO newModule) {
        this.newModule = newModule;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public List<ModuleDTO> getAllTemplateModules(){
        try {
            return client.target(baseUri)
                    .path("/modules/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ModuleDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ModuleDTO> getAllModules(){
        try {
            if (manager.getCurrentTemplate() != null) {
                manager.setCurrentSoftwareId(manager.getCurrentTemplate().getSoftwareCode());
            }
            return client.target(baseUri)
                    .path("/modules/all/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ModuleDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllModules", logger);
            return null;
        }
    }
    
    public String createModule() {
        try {
            newModule.setSoftware_id(manager.getCurrentSoftwareId());
            client.target(baseUri)
                    .path("modules/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newModule));
            newModule.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (manager.getCurrentTemplate() != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String addModule(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addModuleId");
            int module_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/modules/addToTemplate/" + module_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding module in method addModule ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeModule(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeModuleId");
            int module_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/modules/removeFromTemplate/" + module_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing module in method removeModule ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
}
