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
/**
 *
 * @author josea
 */
@Entity
public class Template extends ConfigBase implements Serializable {
    
    public Template() {
    }

    public Template(int id, Client client, String description, String contractData, State state, Software software) {
        super(id, client, description, contractData, state, software);
    }
}
