/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 *
 * @author Ruben Lauro
 */
public class Manager implements Serializable {

    /**
     * Creates a new instance of Manager
     */  
    
    protected Client client;
    
    @ManagedProperty("#{userManager}")
    protected UserManager userManager;
    
    protected static final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";
  
    
    public Manager() { 
        client = ClientBuilder.newClient();
    }
    
    @PostConstruct
    public void init(){
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
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
}
