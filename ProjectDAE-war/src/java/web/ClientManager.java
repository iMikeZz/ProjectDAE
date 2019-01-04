/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.ConfigurationDTO;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import util.URILookup;
import utils.State;

/**
 *
 * @author josea
 */
@ManagedBean(name = "clientManager")
@SessionScoped
public class ClientManager extends Manager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.ClientManager");

    public static final String ALL = "ALL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String SOFTWARE = "SOFTWARE";
    public static final String STATE  = "STATE";
    
    public String searchSoftware;
    public String searchDescription;
    public String searchState;
    
    public List<ConfigurationDTO> myConfigList;
    
    // ------------------------
    // constructors
    // ------------------------
    
    public ClientManager() {
        myConfigList = new LinkedList<>();
        client = ClientBuilder.newClient();
        searchSoftware = "";
        searchDescription = "";
        searchState = "";
    }
    
    @PostConstruct
    public void init_client(){
        search(ALL);
    }
    
    public void search(String search) {
        String filter = "";
        switch(search) {
            case ALL: break;
            case SOFTWARE: 
                filter = "/software/" + searchSoftware;
                break;
            case DESCRIPTION: 
                filter = "/description/" + searchDescription;
                break;
            case STATE: 
                filter = "/state/" + searchState;
                break;
            default: break;
        }
        
        try {
              List<ConfigurationDTO> configs = client.target(URILookup.getBaseAPI())
                .path("/configurations/client")
                .path(userManager.getUsername())
                .path(filter)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ConfigurationDTO>>() {
                });
              myConfigList.clear();
              myConfigList.addAll(configs);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting my configurations in method getMyConfigurations", logger);
        }
    }
    
    public boolean isMyConfigListEmpty() {
        return myConfigList.size() <= 0;
    }
    
    public String[] getAllStates() {
        return Arrays.toString(State.values()).replaceAll("^.|.$", "").split(", ");
    } 
    
    // ------------------------
    // getters & setters
    // ------------------------

    public List<ConfigurationDTO> getMyConfigList() {
        return myConfigList;
    }

    public void setMyConfigList(List<ConfigurationDTO> myConfigList) {
        this.myConfigList = myConfigList;
    }

    public String getSearchSoftware() {
        return searchSoftware;
    }

    public void setSearchSoftware(String searchSoftware) {
        this.searchSoftware = searchSoftware;
    }

    public String getSearchDescription() {
        return searchDescription;
    }

    public void setSearchDescription(String searchDescription) {
        this.searchDescription = searchDescription;
    }

    public String getSearchState() {
        return searchState;
    }

    public void setSearchState(String searchState) {
        this.searchState = searchState;
    }

    public String getALL() {
        return ALL;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public String getSOFTWARE() {
        return SOFTWARE;
    }

    public String getSTATE() {
        return STATE;
    }
}
