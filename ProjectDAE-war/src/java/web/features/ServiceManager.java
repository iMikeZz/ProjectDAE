/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import web.*;
import dtos.ServiceDTO;
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
@ManagedBean(name = "serviceManager")
@SessionScoped
public class ServiceManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());

    private ServiceDTO newService;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    public ServiceManager() {
        this.newService = new ServiceDTO();
    }
    
    public ServiceDTO getNewService() {
        return newService;
    }
    
    public void setNewService(ServiceDTO newService) {
        this.newService = newService;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public List<ServiceDTO> getAllTemplateServices(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/services/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplateServices", logger);
            return null;
        }
    }
    
    public List<ServiceDTO> getAllConfigurationServices(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/services/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration services in method getAllConfigurationServices", logger);
            return null;
        }
    }
    
    public List<ServiceDTO> getAllServices(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/services/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllServices", logger);
            return null;
        }
    }
    
    public String createService() {
        try {
            if (manager.getCurrentTemplate() != null && !manager.getCreationPage().equals("newConfiguration")) {
                newService.setConfig_id(manager.getCurrentTemplate().getId());
            }
            if (manager.getCurrentConfiguration()!= null) {
                newService.setConfig_id(manager.getCurrentConfiguration().getId());
            }
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("services/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newService));
            newService.reset();
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
    
    public void newServiceRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newService");
            manager.setCreationPage(param.getValue().toString());
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String addService(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addServiceId");
            int service_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/services/addToTemplate/" + service_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding sevice in method addService ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeService(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeServiceId");
            int service_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/services/removeFromTemplate/" + service_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing sevice in method removeService ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<ServiceDTO> getServicesNotInTemplate(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/services/servicesNotInTemplate/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getServicesNotInTemplate", logger);
            return null;
        }
    }
    
    //*******************CONFIGURATION******************
    public String addServiceConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addServiceId");
            int service_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/services/addToTemplate/" + service_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding sevice in method addServiceConfiguration ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeServiceConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeServiceId");
            int service_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
                    .path("/services/removeFromConfiguration/" + service_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing sevice in method removeServiceConfiguration ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<ServiceDTO> getServicesNotInConfiguration(){
        try {
            return client.target(URILookup.getBaseAPI())
                    .path("/services/servicesNotInTemplate/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getServicesNotInConfiguration", logger);
            return null;
        }
    }
}
