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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

@Named(value = "administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {
 
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    @EJB
    private AdministratorBean administratorBean;
    
    //Administrator
    private AdministratorDTO currentAdministrator;
    private AdministratorDTO newAdministrator;
    
    @EJB
    private ClientBean clientBean;
    
    //Administrator
    private ClientDTO currentClient;
    private ClientDTO newClient;
    
    public AdministratorManager() {
        this.newAdministrator = new AdministratorDTO();
        
        this.newClient = new ClientDTO();
    }
    
    public String login(){
        return "admin_index?faces-redirect=true";
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
    
    //*********************ADMINISTRATORS*****************************
    public List<AdministratorDTO> getAllAdministrators(){
        try {
            return administratorBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all students in method getAllStudents", logger);
            return null;
        }
    }
    
    public String createAdministrator() {
        try {
            administratorBean.create(
                    newAdministrator.getUsername(),
                    newAdministrator.getPassword(),
                    newAdministrator.getName(),
                    newAdministrator.getEmail(),
                    newAdministrator.getRole());
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
            administratorBean.update(currentAdministrator.getUsername(),
                    currentAdministrator.getPassword(), 
                    currentAdministrator.getName(), 
                    currentAdministrator.getEmail(), 
                    currentAdministrator.getRole());
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
            administratorBean.remove(username);
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
            return clientBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all clients in method getAllClients", logger);
            return null;
        }
    }
    
    public String createClient() {
        try {
            clientBean.create(
                    newClient.getUsername(),
                    newClient.getPassword(),
                    newClient.getAddress(),
                    newClient.getContactPerson(),
                    newClient.getCompanyName());
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
            clientBean.update(
                    currentClient.getUsername(),
                    currentClient.getPassword(),
                    currentClient.getAddress(),
                    currentClient.getContactPerson(),
                    currentClient.getCompanyName());
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
            clientBean.remove(username);
        } 
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing student in method removeClient", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
}
