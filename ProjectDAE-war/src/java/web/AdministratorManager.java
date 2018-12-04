/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;
 
import dtos.AdministratorDTO;
import dtos.ClientDTO;
import ejbs.AdministratorBean;
import ejbs.ClientBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

@ManagedBean(name = "administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {
    
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    @EJB
    private AdministratorBean administratorBean;
    
    //Administrator
    private AdministratorDTO currentAdministrator;
    private AdministratorDTO newAdministrator;
    private int adminsVersion;
    private String searchValue;
    
    @EJB
    private ClientBean clientBean;
    
    //Administrator
    private ClientDTO currentClient;
    private ClientDTO newClient;
    
    
    private Client client;
    private final String baseUri = "http://localhost:8080/ProjectDAE-war/webapi";
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager;
    
    public AdministratorManager() {
        this.newAdministrator = new AdministratorDTO();
        
        this.newClient = new ClientDTO();
        
        
        client = ClientBuilder.newClient();
    }
    
    @PostConstruct
    public void init(){
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
    }
    
    
    //********************GETTERS&SETTERS
    
    public AdministratorBean getAdministratorBean() {
        return administratorBean;
    }

    public void setAdministratorBean(AdministratorBean administratorBean) {
        this.administratorBean = administratorBean;
    }

    public AdministratorDTO getCurrentAdministrator() {
        return currentAdministrator;
    }

    public void setCurrentAdministrator(AdministratorDTO currentAdministrator) {
        this.currentAdministrator = currentAdministrator;
    }

    public AdministratorDTO getNewAdministrator() {
        return newAdministrator;
    }

    public void setNewAdministrator(AdministratorDTO newAdministrator) {
        this.newAdministrator = newAdministrator;
    }

    public ClientBean getClientBean() {
        return clientBean;
    }

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }

    public ClientDTO getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ClientDTO currentClient) {
        this.currentClient = currentClient;
    }

    public ClientDTO getNewClient() {
        return newClient;
    }

    public void setNewClient(ClientDTO newClient) {
        this.newClient = newClient;
    }

    public int getAdminsVersion() {
        return adminsVersion;
    }

    public void setAdminsVersion(int adminsVersion) {
        this.adminsVersion = adminsVersion;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
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
    
    //*********************ADMINISTRATORS*****************************
    public List<AdministratorDTO> getAllAdministrators(){
        try {
            /*
            System.out.println(getAdminsVersion() + "  " + getSearchValue());
            return administratorBean.getAll(getAdminsVersion());
            */
            return client.target(baseUri)
                    .path("/administrators/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<AdministratorDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all students in method getAllAdministrators", logger);
            return null;
        }
    }
    
    public String adminsByName(){
        setAdminsVersion(1);
        System.out.println("passei no admins by name " + getAdminsVersion() + "  " + getSearchValue());
        return "admin_index?faces-redirect=true";
    }
    
    public String createAdministrator() {
        try {
            /*
            administratorBean.create(
                    newAdministrator.getUsername(),
                    newAdministrator.getPassword(),
                    newAdministrator.getName(),
                    newAdministrator.getEmail(),
                    newAdministrator.getRole());
            */
            client.target(baseUri)
                    .path("administrators/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newAdministrator));
            newAdministrator.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "admin_index?faces-redirect=true";
    }
    
    public String updateAdministrator(){
        try {
            /*
            administratorBean.update(currentAdministrator.getUsername(),
                    currentAdministrator.getPassword(), 
                    currentAdministrator.getName(), 
                    currentAdministrator.getEmail(), 
                    currentAdministrator.getRole());
            
            */
            client.target(baseUri)
                    .path("administrators/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentAdministrator));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating administrator in method updateAdministrator", logger);
            return null;
        }
        return "admin_index?facelet-redirect=true";
    }
    
    public String removeAdministrator(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteAdministratorId");
            String username = param.getValue().toString();
            /*
            administratorBean.remove(username);
            */
            client.target(baseUri)
                    .path("administrators/" + username)
                    .request(MediaType.APPLICATION_XML)
                    .delete(Boolean.class);
        } 
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing administrator in method removeAdministrator", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
    
    //********************CLIENTS**************************************
    public List<ClientDTO> getAllClients(){
        try {
            //return clientBean.getAll();
            return client.target(baseUri)
                    .path("/clients/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ClientDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all clients in method getAllClients", logger);
            return null;
        }
    }
    
    public String createClient() {
        try {
            /*
            clientBean.create(
                    newClient.getUsername(),
                    newClient.getPassword(),
                    newClient.getAddress(),
                    newClient.getContactPerson(),
                    newClient.getName());
            */
            
            client.target(baseUri)
                    .path("clients/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newClient));
            newClient.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "admin_index?faces-redirect=true";
    }
    
    public String updateClient(){
        try {
            /*
            clientBean.update(
                    currentClient.getUsername(),
                    currentClient.getPassword(),
                    currentClient.getAddress(),
                    currentClient.getContactPerson(),
                    currentClient.getName());
                     */
            client.target(baseUri)
                    .path("clients/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentClient));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating client in method updateClient", logger);
            return null;
        }
        return "admin_index?facelet-redirect=true";
    }

    public String removeClient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteClientId");
            String username = param.getValue().toString();
            //clientBean.remove(username);
            System.out.println("username");
            client.target(baseUri)
                    .path("/clients/" + username)
                    .request(MediaType.APPLICATION_XML)
                    .delete(Boolean.class);
        } 
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing student in method removeClient", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
}
