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
            Extension extension = new Extension(extensionDTO.getId(), extensionDTO.getExtension(), software);
            software.addExtension(extension);
            em.persist(extension);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all/{id}")
    public List<ExtensionDTO> getAll(@PathParam("id") int id){
        try {
            Software software = em.find(Software.class, id);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            return extensionsToDTO(software.getExtensions());
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
        return new ExtensionDTO(extension.getId(), extension.getExtension(), extension.getSoftware().getId());
    }
    
    
    public List<ExtensionDTO> extensionsToDTO(List<Extension> extensions){
        List<ExtensionDTO> dtos = new ArrayList<>();
        for (Extension extension : extensions) {
            dtos.add(extensionToDTO(extension));
        }
        return dtos;
    }
}
