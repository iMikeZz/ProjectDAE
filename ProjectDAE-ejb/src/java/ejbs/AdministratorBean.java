/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import entities.roles.Administrator;
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
@Path("/administrators")
public class AdministratorBean {
    
    @PersistenceContext
    EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(AdministratorDTO administratorDTO){
        try{
            if (em.find(Administrator.class, administratorDTO.getUsername()) != null) {
                throw new EJBException("Administrator already exists");
            }
            Administrator administrator = new Administrator(administratorDTO.getUsername(), 
                    administratorDTO.getPassword(), administratorDTO.getName(), administratorDTO.getEmail(), administratorDTO.getRole());
            em.persist(administrator);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(AdministratorDTO administratorDTO){
        try{
            Administrator administrator = (Administrator) em.find(Administrator.class, administratorDTO.getUsername());
            if (administrator == null) {
                return;
            }
            
            administrator.setPassword(administratorDTO.getPassword());
            administrator.setName(administratorDTO.getName());
            administrator.setEmail(administratorDTO.getEmail());
            administrator.setRole(administratorDTO.getRole());
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
            Administrator administrator = (Administrator) em.find(Administrator.class, username);
            if (administrator == null) {
                return;
            }
            em.remove(administrator); 
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<AdministratorDTO> getAll(){
        try {
            List<Administrator> admins = em.createNamedQuery("getAllAdministrators").getResultList();
            return administratorsToDTO(admins);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{name}")
    public List<AdministratorDTO> getAll(@PathParam("name") String name){
        try {
            List<Administrator> admins = em.createNamedQuery("getAllAdministratorsByName")
                    .setParameter("name", name)
                    .getResultList();
            return administratorsToDTO(admins);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("allOrderedByUsername")
    public List<AdministratorDTO> getAllAdministratorsOrderedByUsername(){
        try {
            List<Administrator> admins = em.createNamedQuery("getAllAdministratorsOrderedByUsername")
                    .getResultList();
            return administratorsToDTO(admins);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public AdministratorDTO adminToDTO(Administrator admin){
        return new AdministratorDTO(admin.getUsername(), null, admin.getName(), admin.getEmail(), admin.getRole());
    }
    
    public List<AdministratorDTO> administratorsToDTO(List<Administrator> administrators){
        List<AdministratorDTO> dtos = new ArrayList<>();
        for (Administrator admin : administrators) {
            dtos.add(adminToDTO(admin));
        }
        return dtos;
    }
}
