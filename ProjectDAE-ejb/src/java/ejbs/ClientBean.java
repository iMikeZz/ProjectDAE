/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ClientDTO;
import entities.roles.Client;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ruben Lauro
 */
@Stateless
public class ClientBean {
    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String address, String contactPerson, String companyName){
        try{
            Client client = new Client(username, password, address, contactPerson, companyName);
            em.persist(client);   
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public ClientDTO clientToDTO(Client client){
        return new ClientDTO(client.getUsername(), null, client.getAddress(), client.getContactPerson(), client.getCompanyName());
    }
    
    
    public List<ClientDTO> administratorsToDTO(List<Client> clients){
        List<ClientDTO> dtos = new ArrayList<>();
        for (Client client : clients) {
            dtos.add(clientToDTO(client));
        }
        return dtos;
    }
}