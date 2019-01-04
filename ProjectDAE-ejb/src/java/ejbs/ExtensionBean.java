/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.Software;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
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
@Path("/extensions")
public class ExtensionBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create/{id}")
    public void create(ExtensionDTO extensionDTO, @PathParam("id") int config_id){
        create_config(extensionDTO, config_id);
    }
    
    public void create_config(ExtensionDTO extensionDTO, @PathParam("id") int config_id){
        try{
            Software software = em.find(Software.class, extensionDTO.getSoftware_id());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            Extension extension = new Extension(extensionDTO.getId(), extensionDTO.getExtension(), software);
            ConfigBase configBase = em.find(ConfigBase.class, config_id);
            if (configBase == null) {
                software.addExtension(extension);
                em.persist(extension);
            } else{
                software.addExtension(extension);
                configBase.addExtension(extension);
                extension.addConfig(configBase);
                em.persist(extension);
            }
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
    public List<ExtensionDTO> getAllByConfigBase(@PathParam("id") int id){
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
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{id_extension}")
    public void addExtensionToTemplate(TemplateDTO templateDTO, @PathParam("id_extension") int extension_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Extension extension = em.find(Extension.class, extension_id);
            if (extension == null) {
                throw new EJBException("Extension doesn't exists");
            }
            
            if (configBase.getExtensions().contains(extension)) {
                return;
            }
            
            if (extension.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.addExtension(extension);
            extension.addConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{id_extension}")
    public void removeExtensionFromTemplate(TemplateDTO templateDTO, @PathParam("id_extension") int extension_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Extension extension = em.find(Extension.class, extension_id);
            if (extension == null) {
                throw new EJBException("Extension doesn't exists");
            }
            
            if (!extension.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.removeExtension(extension);
            extension.removeConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("extensionsNotInTemplate/{id_config}/{id_software}")
    public List<ExtensionDTO> getExtensionsNotInTemplate(@PathParam("id_config")int id_config, @PathParam("id_software")int id_software) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Software software = em.find(Software.class, id_software);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            
            List<Extension> allExtensions = new ArrayList<>(software.getExtensions());
            
            allExtensions.removeAll(configBase.getExtensions());
            
            return extensionsToDTO(allExtensions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToConfiguration/{id_extension}")
    public void addExtensionToConfiguration(ConfigurationDTO configurationDTO, @PathParam("id_extension") int extension_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Extension extension = em.find(Extension.class, extension_id);
            if (extension == null) {
                throw new EJBException("Extension doesn't exists");
            }
            
            if (configBase.getExtensions().contains(extension)) {
                return;
            }
            
            if (extension.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.addExtension(extension);
            extension.addConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromConfiguration/{id_extension}")
    public void removeExtensionFromConfiguration(ConfigurationDTO configurationDTO, @PathParam("id_extension") int extension_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Extension extension = em.find(Extension.class, extension_id);
            if (extension == null) {
                throw new EJBException("Extension doesn't exists");
            }
            
            if (!extension.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.removeExtension(extension);
            extension.removeConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("extensionsNotInConfiguration/{id_config}/{id_software}")
    public List<ExtensionDTO> getExtensionsNotInConfiguration(@PathParam("id_config")int id_config, @PathParam("id_software")int id_software) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Software software = em.find(Software.class, id_software);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            
            List<Extension> allExtensions = new ArrayList<>(software.getExtensions());
            
            allExtensions.removeAll(configBase.getExtensions());
            
            return extensionsToDTO(allExtensions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public static ExtensionDTO extensionToDTO(Extension extension){
        return new ExtensionDTO(extension.getId(), extension.getExtension(), extension.getSoftware().getId());
    }
    
    
    public static List<ExtensionDTO> extensionsToDTO(List<Extension> extensions){
        List<ExtensionDTO> dtos = new ArrayList<>();
        for (Extension extension : extensions) {
            dtos.add(extensionToDTO(extension));
        }
        return dtos;
    }
}
