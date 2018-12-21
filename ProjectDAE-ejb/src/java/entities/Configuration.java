/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.roles.Client;
import entities.utils.State;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author josea
 */
@Entity
public class Configuration extends ConfigBase implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "CLIENT_CODE")
    @NotNull(message = "A client is needed to this configuration")
    private Client client;
    
    @NotNull
    private State state;
    
    @NotNull
    private String contractData;

    public Configuration() {
    }

    public Configuration(int id, Client client, String description, String contractData, State state, Software software) {
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getContractData() {
        return contractData;
    }

    public void setContractData(String contractData) {
        this.contractData = contractData;
    }
    
    
}
