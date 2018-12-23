/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

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
    
    @EJB
    private TemplateBean templateBean;
    private TemplateDTO currentTemplate;
    private TemplateDTO newTemplate;
   
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
    
    
    


    //*******************TEMPLATES********************************
    public List<TemplateDTO> getAllTemplates(){
        try {
            return client.target(baseUri)
                .path("/templates/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<TemplateDTO>>() {
                });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplates", logger);
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
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplates", logger);
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
    
}
