/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web.features;

import web.*;
import dtos.LicenseDTO;
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
@ManagedBean(name = "licenseManager")
@SessionScoped
public class LicenseManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(LicenseManager.class.getName());
    
    private LicenseDTO newLicense;
    
    private String creationPage;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    public LicenseManager() {
        this.newLicense = new LicenseDTO();
    }
    
    public LicenseDTO getNewLicense() {
        return newLicense;
    }
    
    public void setNewLicense(LicenseDTO newLicense) {
        this.newLicense = newLicense;
    }
    
    public Manager getManager() {
        return manager;
    }
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    //*******************TEMPLATES********************************
    public List<LicenseDTO> getAllTemplateLicenses(){
        try {
            return client.target(baseUri)
                    .path("/licenses/" + manager.getCurrentTemplate().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplateLicenses", logger);
            return null;
        }
    }
    
    public List<LicenseDTO> getAllConfigurationLicenses(){
        try {
            return client.target(baseUri)
                    .path("/licenses/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration licenses in method allConfigurationLicenses", logger);
            return null;
        }
    }
    
    public List<LicenseDTO> getAllLicenses(){
        try {
            if (manager.getCurrentTemplate() != null) {
                manager.setCurrentSoftwareId(manager.getCurrentTemplate().getSoftwareCode());
            }
            return client.target(baseUri)
                    .path("/licenses/all/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllLicenses", logger);
            return null;
        }
    }
    
    public String createLicense() {
        try {
            newLicense.setSoftware_id(manager.getCurrentSoftwareId());
            if (manager.getCurrentTemplate() != null){ //significa q estamos no update
                client.target(baseUri)
                        .path("licenses/create/" + manager.getCurrentTemplate().getId())
                        .request(MediaType.APPLICATION_XML)
                        .post(Entity.xml(newLicense));
                newLicense.reset();
            }else if(manager.getCurrentConfiguration() != null){
                client.target(baseUri)
                    .path("licenses/create/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newLicense));
                newLicense.reset();  
            }else{
                client.target(baseUri)
                        .path("licenses/create/" + 0)
                        .request(MediaType.APPLICATION_XML)
                        .post(Entity.xml(newLicense));
                newLicense.reset();
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
    
    public void newLicenseRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newLicense");
            creationPage = param.getValue().toString();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String addLicense(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/licenses/addToTemplate/" + license_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding license in method addLicense ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeLicense(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/licenses/removeFromTemplate/"+ license_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentTemplate()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing license in method removeLicense", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<LicenseDTO> getLicensesNotInTemplate(){
        try {
            manager.setCurrentSoftwareId(manager.getCurrentTemplate().getSoftwareCode());
            return client.target(baseUri)
                    .path("/licenses/licensesNotInTemplate/" + manager.getCurrentTemplate().getId()+ "/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getLicensesNotInTemplate", logger);
            return null;
        }
    }
    
    
    //******************CONFIGURATIONS***********************
   
    public List<LicenseDTO> getAllLicensesConfiguration(){
        try {
            if (manager.getCurrentConfiguration() != null) {
                manager.setCurrentSoftwareId(manager.getCurrentConfiguration().getSoftwareCode());
            }
            return client.target(baseUri)
                    .path("/licenses/all/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllLicensesConfiguration", logger);
            return null;
        }
    }
    
    public String createLicenseConfiguration() {
        try {
            newLicense.setSoftware_id(manager.getCurrentSoftwareId());
            if (manager.getCurrentConfiguration() != null){ //significa q estamos no update
                client.target(baseUri)
                        .path("licenses/create/" + manager.getCurrentConfiguration().getId())
                        .request(MediaType.APPLICATION_XML)
                        .post(Entity.xml(newLicense));
                newLicense.reset();
            }else{
                client.target(baseUri)
                        .path("licenses/create/" + 0)
                        .request(MediaType.APPLICATION_XML)
                        .post(Entity.xml(newLicense));
                newLicense.reset();
            }
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (manager.getCurrentTemplate() != null)
            return "/admin/configurations/admin_configuration_update?faces-redirect=true";
        
        return "/admin/configurations/admin_configuration_create?faces-redirect=true";
    }
    
    public String addLicenseConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/licenses/addToConfiguration/" + license_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding license in method addLicense ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeLicenseConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/licenses/removeFromConfiguration/"+ license_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(manager.getCurrentConfiguration()));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing license in method removeLicense", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public List<LicenseDTO> getLicensesNotInConfiguration(){
        try {
            manager.setCurrentSoftwareId(manager.getCurrentConfiguration().getSoftwareCode());
            return client.target(baseUri)
                    .path("/licenses/licensesNotInTemplate/" + manager.getCurrentConfiguration().getId()+ "/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getLicensesNotInConfiguration", logger);
            return null;
        }
    }
}
