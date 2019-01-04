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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import util.URILookup;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "parameterManager")
@SessionScoped
public class ParameterManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ParameterManager.class.getName());
    
    private ParameterDTO newParameter;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
   
    public ParameterManager() {
        this.newParameter = new ParameterDTO();
    }
    
    public ParameterDTO getNewParameter() {
        return newParameter;
    }
    
    public void setNewParameter(ParameterDTO newParameter) {
        this.newParameter = newParameter;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public List<ParameterDTO> getAllTemplateParameters(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/parameters/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ParameterDTO> getAllConfigurationParameters(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/parameters/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration parameters in method getAllConfigurationParameters", logger);
            return null;
        }
    }
    
    public List<ParameterDTO> getAllParameters(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/parameters/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllParameters", logger);
            return null;
        }
    }
    
    public String createParameter() {
        try {
            if (manager.getCurrentTemplate() != null && !manager.getCreationPage().equals("newConfiguration")) {
                newParameter.setConfig_id(manager.getCurrentTemplate().getId());
            }
            if (manager.getCurrentConfiguration()!= null) {
                newParameter.setConfig_id(manager.getCurrentConfiguration().getId());
            }
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("parameters/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newParameter));
            newParameter.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        
         if (manager.getCreationPage().equals("newTemplate")){
            return "/admin/templates/admin_template_create?faces-redirect=true";
        }
        if (manager.getCreationPage().equals("newConfiguration")){
            return "/admin/configurations/admin_configuration_create?faces-redirect=true";
        }
        if (manager.getCurrentConfiguration() != null){
            return "/admin/configurations/admin_configuration_update?faces-redirect=true";
        }
        if (manager.getCurrentTemplate() != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return null;
    }
   
    public void newParameterRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newParameter");
            manager.setCreationPage(param.getValue().toString());
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String addParameter(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addParameterId");
            int parameter_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/parameters/addToTemplate/" + parameter_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding parameter in method addParameter ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeParameter(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeParameterId");
            int parameter_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/parameters/removeFromTemplate/" + parameter_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing parameter in method removeParameter ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<ParameterDTO> getParametersNotInTemplate(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/parameters/parametersNotInTemplate/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getParametersNotInTemplate", logger);
            return null;
        }
    }
    
    //***************CONFIGURATIONS********************
    public String addParameterConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addParameterId");
            int parameter_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/parameters/addToConfiguration/" + parameter_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding parameter in method addParameterConfiguration ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeParameterConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeParameterId");
            int parameter_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/parameters/removeFromConfiguration/" + parameter_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing parameter in method removeParameterConfiguration ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<ParameterDTO> getParametersNotInConfiguration(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/parameters/parametersNotInTemplate/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getParametersNotInConfiguration", logger);
            return null;
        }
    }
}
