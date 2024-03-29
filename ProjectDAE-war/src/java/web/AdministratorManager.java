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
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import util.URILookup;

@ManagedBean(name = "administratorManager")
@SessionScoped
public class AdministratorManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private static final String ALLADMINS = "ALLADMINS";
    private static final String SEARCHBYNAME = "SEARCHBYNAME";
    private static final String SORTBYNAME = "SORTBYNAME";
    private static final String SORTBYUSERNAME = "SORTBYUSERNAME";
    
    private static final String ALLCLIENTS = "ALLCLIENTS";
    private static final String SEARCHCLIENTSBYNAME = "SEARCHCLIENTSBYNAME";
    private static final String SORTCLIENTSBYNAME = "SORTCLIENTSBYNAME";
    private static final String SORTCLIENTSBYUSERNAME = "SORTCLIENTSBYUSERNAME";
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    @EJB
    private AdministratorBean administratorBean;
    
    //Administrator
    private AdministratorDTO currentAdministrator;
    private AdministratorDTO newAdministrator;
    private String adminsVersion = ALLADMINS;
    private String searchValue;
    
    @EJB
    private ClientBean clientBean;
    
    //Client
    private ClientDTO currentClient;
    private ClientDTO newClient;
    private String clientsVersion = ALLCLIENTS;
    private String clientsSearchValue;
    
    public AdministratorManager() {
        this.newAdministrator = new AdministratorDTO();
        
        this.newClient = new ClientDTO();
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
    
    public String getAdminsVersion() {
        return adminsVersion;
    }
    
    public void setAdminsVersion(String adminsVersion) {
        this.adminsVersion = adminsVersion;
    }
    
    public String getSearchValue() {
        return searchValue;
    }
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    public String getClientsVersion() {
        return clientsVersion;
    }
    
    public void setClientsVersion(String clientsVersion) {
        this.clientsVersion = clientsVersion;
    }
    
    public String getClientsSearchValue() {
        return clientsSearchValue;
    }
    
    public void setClientsSearchValue(String clientsSearchValue) {
        this.clientsSearchValue = clientsSearchValue;
    }
    
    public String getSEARCHBYNAME() {
        return SEARCHBYNAME;
    }
    
    public String getALLADMINS() {
        return ALLADMINS;
    }
    
    public String getSORTBYNAME() {
        return SORTBYNAME;
    }
    
    public String getSORTBYUSERNAME() {
        return SORTBYUSERNAME;
    }
    
    public String getALLCLIENTS() {
        return ALLCLIENTS;
    }
    
    public String getSEARCHCLIENTSBYNAME() {
        return SEARCHCLIENTSBYNAME;
    }
    
    public String getSORTCLIENTSBYNAME() {
        return SORTCLIENTSBYNAME;
    }
    
    public String getSORTCLIENTSBYUSERNAME() {
        return SORTCLIENTSBYUSERNAME;
    }
        
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    //*********************ADMINISTRATORS*****************************
    public List<AdministratorDTO> getAllAdministrators(){
        try {
            switch (adminsVersion) {
                case ALLADMINS:
                    return getAdminsListByUrl("/administrators/all");
                case SEARCHBYNAME:
                    if (!searchValue.equals("")) {
                        return getAdminsListByUrl("/administrators/" + searchValue);
                    }
                    return getAdminsListByUrl("/administrators/all");
                case SORTBYUSERNAME:
                    return getAdminsListByUrl("/administrators/allOrderedByUsername");
                case SORTBYNAME:
                    return getAdminsListByUrl("/administrators/all");
                default:
                    return null;
            }
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all students in method getAllAdministrators", logger);
            return null;
        }
    }
    
    public String createAdministrator() {
        try {
            client.target(URILookup.getBaseAPI())
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
            client.target(URILookup.getBaseAPI())
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
            client.target(URILookup.getBaseAPI())
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
            switch (clientsVersion){
                case ALLCLIENTS:
                    return getClientsListByUrl("/clients/all");
                case SEARCHCLIENTSBYNAME:
                    if (!clientsSearchValue.equals("")) {
                        return getClientsListByUrl("/clients/" + clientsSearchValue);
                    }
                    return getClientsListByUrl("/clients/all");
                case SORTCLIENTSBYUSERNAME:
                    return getClientsListByUrl("/clients/allOrderedByUsername");
                case SORTCLIENTSBYNAME:
                    return getClientsListByUrl("/clients/all");
                default:
                    return null;
            }
            
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all clients in method getAllClients", logger);
            return null;
        }
    }
    
    public List<ClientDTO> getAllClientsExceptMe(){
        try {
            return getClientsListByUrl("/clients/allExceptMe/" + manager.getCurrentConfiguration().getClientUsername());           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all clients in method getAllClients", logger);
            return null;
        }
    }    
    
    public String createClient() {
        try {
            client.target(URILookup.getBaseAPI())
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
            client.target(URILookup.getBaseAPI())
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
            client.target(URILookup.getBaseAPI())
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
    
    //**************COSTUM METHODS
    private List<ClientDTO> getClientsListByUrl(String url){
        return client.target(URILookup.getBaseAPI())
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ClientDTO>>() {
                });
    }
    
    private List<AdministratorDTO> getAdminsListByUrl(String url){
        return client.target(URILookup.getBaseAPI())
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<AdministratorDTO>>() {
                });
    }
}
