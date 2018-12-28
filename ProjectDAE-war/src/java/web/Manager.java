/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import dtos.TemplateDTO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "manager")
@SessionScoped
public class Manager implements Serializable {
    
    protected Client client;
    
    @ManagedProperty("#{userManager}")
    protected UserManager userManager;
    
    protected static final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";
    
    protected int currentSoftwareId = 1;
    
    protected TemplateDTO currentTemplate;
    protected TemplateDTO newTemplate;
    
    public Manager() {
        client = ClientBuilder.newClient();
        
        this.newTemplate = new TemplateDTO();
    }
    
    @PostConstruct
    public void init(){
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
    }
    
    public void softwareChangeListener(ValueChangeEvent e){
        currentSoftwareId = Integer.parseInt(e.getNewValue().toString());
        
        System.out.println(currentSoftwareId);
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    public UserManager getUserManager() {
        return userManager;
    }
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    public int getCurrentSoftwareId() {
        return currentSoftwareId;
    }
    
    public void setCurrentSoftwareId(int currentSoftwareId) {
        this.currentSoftwareId = currentSoftwareId;
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
}
