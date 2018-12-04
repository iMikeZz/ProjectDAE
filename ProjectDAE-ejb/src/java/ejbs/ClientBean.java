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
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ruben Lauro
 */
@DeclareRoles({"Administrator"})
@Stateless
@Path("/clients")
public class ClientBean {
    
    @PersistenceContext
    EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ClientDTO clientDTO){
        try{
            if (em.find(Client.class, clientDTO.getUsername()) != null) {
                throw new EJBException("Client already exists");
            }
            Client client = new Client(clientDTO.getUsername()
                    , clientDTO.getPassword(), clientDTO.getAddress(), clientDTO.getContactPerson(), clientDTO.getName());
            em.persist(client);   
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(ClientDTO clientDTO){
        try{
            Client client = (Client) em.find(Client.class, clientDTO.getUsername());
            if (client == null) {
                return;
            }
            
            client.setPassword(clientDTO.getPassword());
            client.setName(clientDTO.getName());
            client.setAddress(clientDTO.getAddress());
            client.setContactPerson(clientDTO.getContactPerson());
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @DELETE
    @RolesAllowed({"Administrator"})
    @Path("{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") String username){
        try{
            Client client = (Client) em.find(Client.class, username);
            if (client == null) {
                return;
            }
            em.remove(client); 
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ClientDTO> getAll(){
        try {
            List<Client> clients = em.createNamedQuery("getAllClients").getResultList();
            return clientsToDTO(clients);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ClientDTO clientToDTO(Client client){
        return new ClientDTO(client.getUsername(), null, client.getAddress(), client.getContactPerson(), client.getName());
    }
    
    
    public List<ClientDTO> clientsToDTO(List<Client> clients){
        List<ClientDTO> dtos = new ArrayList<>();
        for (Client client : clients) {
            dtos.add(clientToDTO(client));
        }
        return dtos;
    }
}
