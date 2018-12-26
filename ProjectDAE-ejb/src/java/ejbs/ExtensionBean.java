/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ExtensionDTO;
import entities.ConfigBase;
import entities.Extension;
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
@Path("/extensions")
public class ExtensionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ExtensionDTO extensionDTO){
        try{
            Software software = em.find(Software.class, extensionDTO.getSoftware_id());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            ConfigBase config = em.find(ConfigBase.class, extensionDTO.getConfig_id());
            if (config == null) {
                throw new EJBException("Config doesn't exists");
            }
            Extension extension_obj = new Extension(extensionDTO.getId(), extensionDTO.getExtension(), software);
            config.addExtension(extension_obj);
            extension_obj.addConfig(config);
            em.persist(extension_obj);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ExtensionDTO> getAll(){
        try {
            List<Extension> extensions = em.createNamedQuery("getAllExtensions").getResultList();
            return extensionsToDTO(extensions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ExtensionDTO> getAllByTemplate(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return extensionsToDTO(configBase.getExtensions());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ExtensionDTO extensionToDTO(Extension extension){
        return new ExtensionDTO(extension.getId(), extension.getExtension(), extension.getSoftware().getId(), 0);
    }
    
    
    public List<ExtensionDTO> extensionsToDTO(List<Extension> extensions){
        List<ExtensionDTO> dtos = new ArrayList<>();
        for (Extension extension : extensions) {
            dtos.add(extensionToDTO(extension));
        }
        return dtos;
    }
}
