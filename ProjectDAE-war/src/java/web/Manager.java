/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import dtos.ConfigurationDTO;
import dtos.QuestionDTO;
import dtos.TemplateDTO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    
    protected int currentSoftwareId = 1;
    
    protected TemplateDTO currentTemplate;
        
    protected ConfigurationDTO currentConfiguration;
    
    protected QuestionDTO currentQuestion;

    protected String currentClientUsername = null;
    
    public Manager() {
        client = ClientBuilder.newClient();
    }
    
    @PostConstruct
    public void init(){
        if (userManager.getUsername() != null && userManager.getPassword() != null) {
            clientRegister(userManager.getUsername(), userManager.getPassword());
        }  
    }
    
    public void clientRegister(String username, String password){   
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
        client.register(feature);
    }
    
    public void softwareChangeListener(ValueChangeEvent e){
        currentSoftwareId = Integer.parseInt(e.getNewValue().toString());
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
    
    public ConfigurationDTO getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(ConfigurationDTO currentConfiguration) {
        this.currentConfiguration = currentConfiguration;
    }

    public QuestionDTO getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(QuestionDTO currentQuestion) {
        this.currentQuestion = currentQuestion;
    }    

    public String getCurrentClientUsername() {
        return currentClientUsername;
    }

    public void setCurrentClientUsername(String currentClientUsername) {
        this.currentClientUsername = currentClientUsername;
    }
    
}
