/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ExtensionDTO;
import dtos.ModuleDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.License;
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
@Path("/modules")
public class ModuleBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create/{id}")
    public void create(ModuleDTO moduleDTO, @PathParam("id")int config_id){
        try{
            Software software = em.find(Software.class, moduleDTO.getSoftware_id());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            Module module = new Module(moduleDTO.getId(), moduleDTO.getDescription(), software);
            ConfigBase configBase = em.find(ConfigBase.class, config_id);
            if (configBase == null) {
                software.addModule(module);
                em.persist(module);
            } else {
                software.addModule(module);
                configBase.addModule(module);
                module.addConfig(configBase);
                em.persist(module);
            }
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all/{id}")
    public List<ModuleDTO> getAll(@PathParam("id") int id){
        try {
            Software software = em.find(Software.class, id);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            return modulesToDTO(software.getModules());
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
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{module_id}")
    public void addModuleToTemplate(TemplateDTO templateDTO, @PathParam("module_id") int module_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Module module = em.find(Module.class, module_id);
            if (module == null) {
                throw new EJBException("Module doesn't exists");
            }
            
            if (configBase.getModules().contains(module)) {
                return;
            }
            
            if (module.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.addModule(module);
            module.addConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{license_id}")
    public void removeModuleFromTemplate(TemplateDTO templateDTO,@PathParam("module_id") int module_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            License license = em.find(License.class, module_id);
            if (license == null) {
                throw new EJBException("License doesn't exists");
            }
            
            if (!license.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.removeLicense(license);
            license.removeConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("modulesNotInTemplate/{id_config}/{id_software}")
    public List<ModuleDTO> getModulesNotInTemplate(@PathParam("id_config")int id_config, @PathParam("id_software")int id_software) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Software software = em.find(Software.class, id_software);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            
            List<Module> allModules = new ArrayList<>(software.getModules());
            
            allModules.removeAll(configBase.getModules());
            
            return modulesToDTO(allModules);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ModuleDTO moduleToDTO(Module module){
        return new ModuleDTO(module.getId(), module.getDescription(), module.getSoftware().getId());
    }
    
    
    public List<ModuleDTO> modulesToDTO(List<Module> modules){
        List<ModuleDTO> dtos = new ArrayList<>();
        for (Module module : modules) {
            dtos.add(moduleToDTO(module));
        }
        return dtos;
    }
}
