/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.roles.Client;
import utils.State;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author josea
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "getAllConfigurations",
            query = "SELECT t FROM Configuration t ORDER BY t.id"
    ),
    @NamedQuery(
            name = "getAllConfigurationsByDescription",
            query = "SELECT t FROM Configuration t WHERE UPPER(t.description) LIKE UPPER(:description) ORDER BY t.description"
    ),
})
public class Configuration extends ConfigBase implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "CLIENT_CODE")
    @NotNull(message = "A client is needed to this configuration")
    private Client client;
    
    @NotNull(message = "Configuration needs a state")
    private String state;
    
    @NotNull
    private String contractData;

    public Configuration() {
    }

    public Configuration(int id, Client client, String description, String contractData, String state, Software software) {
        super(id, description, software);
        this.client = client;
        this.state = state;
        this.contractData = contractData;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContractData() {
        return contractData;
    }

    public void setContractData(String contractData) {
        this.contractData = contractData;
    }
    
    
}
