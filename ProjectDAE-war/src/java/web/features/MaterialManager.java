/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import dtos.LicenseDTO;
import web.*;
import dtos.MaterialDTO;
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
@ManagedBean(name = "materialManager")
@SessionScoped
public class MaterialManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(MaterialManager.class.getName());

    private MaterialDTO newMaterial;
    
    private String creationPage;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;

    public MaterialManager() {
        this.newMaterial = new MaterialDTO();
    }
    
    public MaterialDTO getNewMaterial() {
        return newMaterial;
    }
    
    public void setNewMaterial(MaterialDTO newMaterial) {
        this.newMaterial = newMaterial;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    //MATERIALS
    public List<MaterialDTO> getAllTemplateMaterials(){
        try {
            return client.target(baseUri)
                    .path("/materials/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<MaterialDTO> getAllConfigurationMaterials(){
        try {
            return client.target(baseUri)
                    .path("/materials/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration materials in method getAllConfigurationMaterials", logger);
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
    
    public String createMaterial() {
        try {
            if (manager.getCurrentTemplate() != null) {
                newMaterial.setConfig_id(manager.getCurrentTemplate().getId());
            }
            if (manager.getCurrentConfiguration() != null) {
                newMaterial.setConfig_id(manager.getCurrentConfiguration().getId());
            }
            client.target(baseUri)
                    .path("materials/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newMaterial));
            newMaterial.reset();
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
    
    public void newMaterialRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newMaterial");
            creationPage = param.getValue().toString();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String cancelCreateMaterial(){
        newMaterial.reset();
        if (manager.getCurrentTemplate() != null)
           return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String addMaterial(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addMaterialId");
            int material_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/materials/addToTemplate/" + material_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding material in method addMaterial ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeMaterial(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeMaterialId");
            int material_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/materials/removeFromTemplate/" + material_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing material in method removeMaterial ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<MaterialDTO> getMaterialsNotInTemplate(){
        try {
            return client.target(baseUri)
                    .path("/materials/materialsNotInTemplate/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getMaterialsNotInTemplate", logger);
            return null;
        }
    }
    
    //**************CONFIGURATION***********************
    
    public String createMaterialConfiguration() {
        try {
            if (manager.getCurrentConfiguration() != null) {
                newMaterial.setConfig_id(manager.getCurrentConfiguration().getId());
            }
            client.target(baseUri)
                    .path("materials/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newMaterial));
            newMaterial.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (manager.getCurrentTemplate() != null)
            return "/admin/configurations/admin_configuration_update?faces-redirect=true";
        
        return "/admin/configurations/admin_configuration_create?faces-redirect=true";
    }
    
    public String addMaterialConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addMaterialId");
            int material_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/materials/addToConfiguration/" + material_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding material in method addMaterialConfiguration ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeMaterialConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeMaterialId");
            int material_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/materials/removeFromConfiguration/" + material_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing material in method removeMaterial ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<MaterialDTO> getMaterialsNotInConfiguration(){
        try {
            return client.target(baseUri)
                    .path("/materials/materialsNotInConfiguration/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getMaterialsNotInConfiguration", logger);
            return null;
        }
    }
    
}
