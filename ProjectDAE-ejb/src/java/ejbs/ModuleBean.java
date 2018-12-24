/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ModuleDTO;
import entities.ConfigBase;
import entities.Module;
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
@Path("/modules")
public class ModuleBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    public void create(int id, String description, int software_id){
        try{
            Software software = em.find(Software.class, software_id);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            em.persist(new Module(id, description, software));
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ModuleDTO> getAll(){
        try {
            List<Module> modules = em.createNamedQuery("getAllModules").getResultList();
            return modulesToDTO(modules);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ModuleDTO moduleToDTO(Module module){
        return new ModuleDTO(module.getId(), module.getDescription());
    }
    
    
    public List<ModuleDTO> modulesToDTO(List<Module> modules){
        List<ModuleDTO> dtos = new ArrayList<>();
        for (Module module : modules) {
            dtos.add(moduleToDTO(module));
        }
        return dtos;
    }
}
