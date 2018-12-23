/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.SoftwareDTO;
import entities.Software;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ruben Lauro
 */
@DeclareRoles({"Administrator"})
@Stateless
@Path("/softwares")
public class SoftwareBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    public void create(int id, String name, String version){
        try{
           em.persist(new Software(id, name, version));
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<SoftwareDTO> getAll(){
        try {
            List<Software> softwares = em.createNamedQuery("getAllSoftwares").getResultList();
            return softwaresToDTO(softwares);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public SoftwareDTO softwareToDTO(Software software){
        return new SoftwareDTO(software.getId(), software.getName(), software.getVersion());
    }
    
    
    public List<SoftwareDTO> softwaresToDTO(List<Software> softwares){
        List<SoftwareDTO> dtos = new ArrayList<>();
        for (Software software : softwares) {
            dtos.add(softwareToDTO(software));
        }
        return dtos;
    }
}
