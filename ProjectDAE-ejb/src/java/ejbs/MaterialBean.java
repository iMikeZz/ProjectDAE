/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.MaterialDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.License;
import entities.Material;
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
@Path("/materials")
public class MaterialBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(MaterialDTO materialDTO){
        try{
            ConfigBase config = em.find(ConfigBase.class, materialDTO.getConfig_id());
            if (config == null) {
                em.persist(new Material(materialDTO.getId(), materialDTO.getDescription(), materialDTO.getImgUrl()));
            } else{
                Material material = new Material(materialDTO.getId(), materialDTO.getDescription(), materialDTO.getImgUrl(), config);
                config.addMaterial(material);
                material.setConfig(config);
                em.persist(material);
            }
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<MaterialDTO> getAll(){
        try {
            List<Material> materials = em.createNamedQuery("getAllMaterials").getResultList();
            
            return materialsToDTO(materials);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<MaterialDTO> getAllByConfigBase(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return materialsToDTO(configBase.getMaterials());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("material/{id}")
    public MaterialDTO getMaterial(@PathParam("id") int id){
        try {
            Material material = em.find(Material.class, id);
            if (material == null) {
                throw new EJBException("Material doesn't exists");
            }
            return materialToDTO(material);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{material_id}")
    public void addMaterialToTemplate(TemplateDTO templateDTO, @PathParam("material_id") int material_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Material material = em.find(Material.class, material_id);
            if (material == null) {
                throw new EJBException("Material doesn't exists");
            }
            
            if (configBase.getMaterials().contains(material)) {
                return;
            }
            
            configBase.addMaterial(material);
            material.setConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{material_id}")
    public void removeMaterialFromTemplate(TemplateDTO templateDTO,@PathParam("material_id") int material_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Material material = em.find(Material.class, material_id);
            if (material == null) {
                throw new EJBException("Material doesn't exists");
            }
            
            if (!configBase.getMaterials().contains(material)) {
                return;
            }
            
            configBase.removeMaterial(material);
            material.setConfig(null);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("materialsNotInTemplate/{id_config}")
    public List<MaterialDTO> getMaterialsNotInTemplate(@PathParam("id_config")int id_config) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            List<Material> allMaterials = em.createNamedQuery("getAllMaterials").getResultList();
            
            allMaterials.removeAll(configBase.getMaterials());
            
            return materialsToDTO(allMaterials);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(MaterialDTO materialDTO){
        try{
            Material material = em.find(Material.class, materialDTO.getId());
            if (material == null) {
                throw new EJBException("Material doesn't exists");
            }
            material.setImgUrl(materialDTO.getImgUrl());
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    //*************CONFIGURATIONS***************    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToConfiguration/{material_id}")
    public void addMaterialToConfiguration(ConfigurationDTO configurationDTO, @PathParam("material_id") int material_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Material material = em.find(Material.class, material_id);
            if (material == null) {
                throw new EJBException("Material doesn't exists");
            }
            
            if (configBase.getMaterials().contains(material)) {
                return;
            }
            
            configBase.addMaterial(material);
            material.setConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromConfiguration/{material_id}")
    public void removeMaterialFromConfiguration(ConfigurationDTO configurationDTO,@PathParam("material_id") int material_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Material material = em.find(Material.class, material_id);
            if (material == null) {
                throw new EJBException("Material doesn't exists");
            }
            
            if (!configBase.getMaterials().contains(material)) {
                return;
            }
            
            configBase.removeMaterial(material);
            material.setConfig(null);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("materialsNotInConfiguration/{id_config}")
    public List<MaterialDTO> getMaterialsNotInConfiguration(@PathParam("id_config")int id_config) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            List<Material> allMaterials = em.createNamedQuery("getAllMaterials").getResultList();
            
            allMaterials.removeAll(configBase.getMaterials());
            
            return materialsToDTO(allMaterials);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public static MaterialDTO materialToDTO(Material material){
        if (material.getConfig() != null) {
            return new MaterialDTO(material.getId(), material.getDescription(), material.getImgUrl(), material.getConfig().getId());
        }
        return new MaterialDTO(material.getId(), material.getDescription(), material.getImgUrl());
    }
    
    
    public static List<MaterialDTO> materialsToDTO(List<Material> materials){
        List<MaterialDTO> dtos = new ArrayList<>();
        for (Material material : materials) {
            dtos.add(materialToDTO(material));
        }
        return dtos;
    }
}
