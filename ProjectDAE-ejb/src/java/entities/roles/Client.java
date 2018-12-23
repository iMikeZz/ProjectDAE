/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.roles;

import entities.Configuration;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "getAllClients",
            query = "SELECT t FROM Client t ORDER BY t.name"
    ),
    @NamedQuery(
            name = "getAllClientsByName",
            query = "SELECT t FROM Client t WHERE UPPER(t.name) LIKE UPPER(:name) ORDER BY t.name"
    ),
    @NamedQuery(
            name = "getAllClientsOrderedByUsername",
            query = "SELECT t FROM Client t ORDER BY t.username"
    )
})
public class Client extends User implements Serializable {
    
    @NotNull(message = "Address can not be empty")
    private String address;
    
    @NotNull(message = "Contact person can not be empty")
    private String contactPerson;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Configuration> configurations;

    public Client() {
        configurations = new LinkedList<>();
    }
    
    public Client(String username, String password, String address, String contactPerson, String companyName) {
        super(username, password, companyName, UserGroup.GROUP.Client);
        this.address = address;
        this.contactPerson = contactPerson;
        
        configurations = new LinkedList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }
}
