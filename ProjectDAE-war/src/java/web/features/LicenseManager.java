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
import util.URILookup;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "licenseManager")
@SessionScoped
public class LicenseManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(LicenseManager.class.getName());
    
    private LicenseDTO newLicense;
    
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
            return client.target(URILookup.getBaseAPI())
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
            return client.target(URILookup.getBaseAPI())
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
            return client.target(URILookup.getBaseAPI())
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
            if (manager.getCurrentTemplate() != null && !manager.getCreationPage().equals("newConfiguration")){ //significa q estamos no update
                manager.clientRegister(userManager.getUsername(), userManager.getPassword());
                manager.getClient().target(URILookup.getBaseAPI())
                        .path("licenses/create/" + manager.getCurrentTemplate().getId())
                        .request(MediaType.APPLICATION_XML)
                        .post(Entity.xml(newLicense));
                newLicense.reset();
                return "/admin/templates/admin_template_update?faces-redirect=true";
            }else if(manager.getCurrentConfiguration() != null){
                manager.clientRegister(userManager.getUsername(), userManager.getPassword());
                manager.getClient().target(URILookup.getBaseAPI())
                    .path("licenses/create/" + manager.getCurrentConfiguration().getId())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newLicense));
                newLicense.reset();  
                return "/admin/configurations/admin_configuration_update?faces-redirect=true";
            }else{
                manager.clientRegister(userManager.getUsername(), userManager.getPassword());
                manager.getClient().target(URILookup.getBaseAPI())
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
        if (manager.getCreationPage().equals("newTemplate")){
            return "/admin/templates/admin_template_create?faces-redirect=true";
        }
        if (manager.getCreationPage().equals("newConfiguration")){
            return "/admin/configurations/admin_configuration_create?faces-redirect=true";
        }
        return null;
    }
    
    public void newLicenseRedirect(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("newLicense");
            manager.setCreationPage(param.getValue().toString());
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String addLicense(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
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
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
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
            return client.target(URILookup.getBaseAPI())
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
            return client.target(URILookup.getBaseAPI())
                    .path("/licenses/all/" + manager.getCurrentSoftwareId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllLicensesConfiguration", logger);
            return null;
        }
    }
    
    public String addLicenseConfiguration(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
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
            manager.clientRegister(userManager.getUsername(), userManager.getPassword());
            manager.getClient().target(URILookup.getBaseAPI())
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
            return client.target(URILookup.getBaseAPI())
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
