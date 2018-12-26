/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.MaterialDTO;
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
@Path("/modules")
public class ModuleBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ModuleDTO moduleDTO){
        try{
            Software software = em.find(Software.class, moduleDTO.getSoftware_id());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            ConfigBase config = em.find(ConfigBase.class, moduleDTO.getConfig_id());
            if (config == null) {
                throw new EJBException("Config doesn't exists");
            }
            Module module = new Module(moduleDTO.getId(), moduleDTO.getDescription(), software);
            module.addConfig(config);
            config.addModule(module);
            em.persist(module);
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
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ModuleDTO> getAllByTemplate(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return modulesToDTO(configBase.getModules());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ModuleDTO moduleToDTO(Module module){
        return new ModuleDTO(module.getId(), module.getDescription(), module.getSoftware().getId(), 0);
    }
    
    
    public List<ModuleDTO> modulesToDTO(List<Module> modules){
        List<ModuleDTO> dtos = new ArrayList<>();
        for (Module module : modules) {
            dtos.add(moduleToDTO(module));
        }
        return dtos;
    }
}
