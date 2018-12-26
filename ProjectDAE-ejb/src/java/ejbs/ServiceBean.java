/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.RepositoryDTO;
import dtos.ServiceDTO;
import entities.ConfigBase;
import entities.Service;
import entities.Software;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("/services")
public class ServiceBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ServiceDTO serviceDTO){
        try{
            ConfigBase config = em.find(ConfigBase.class, serviceDTO.getConfig_id());
            if (config == null) {
                throw new EJBException("Config doesn't exists");
            }
            em.persist(new Service(serviceDTO.getId(), serviceDTO.getService(), config));
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ServiceDTO> getAll(){
        try {
            List<Service> services = em.createNamedQuery("getAllServices").getResultList();
            return servicesToDTO(services);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ServiceDTO> getAllByTemplate(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return servicesToDTO(configBase.getServices());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ServiceDTO serviceToDTO(Service service){
        return new ServiceDTO(service.getId(), service.getService(), service.getConfig().getId());
    }
    
    
    public List<ServiceDTO> servicesToDTO(List<Service> services){
        List<ServiceDTO> dtos = new ArrayList<>();
        for (Service service : services) {
            dtos.add(serviceToDTO(service));
        }
        return dtos;
    }
}
