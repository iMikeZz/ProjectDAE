/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.MaterialDTO;
import dtos.SoftwareDTO;
import entities.ConfigBase;
import entities.Material;
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
@Path("/materials")
public class MaterialBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    public void create(int id, String description, String imgUrl, int config_id){
        try{
            ConfigBase config = em.find(ConfigBase.class, config_id);
            if (config == null) {
                throw new EJBException("Config doesn't exists");
            }
            em.persist(new Material(id, description, imgUrl, config));
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
    
    public MaterialDTO materialToDTO(Material material){
        return new MaterialDTO(material.getId(), material.getDescription(), material.getImgUrl());
    }
    
    
    public List<MaterialDTO> materialsToDTO(List<Material> materials){
        List<MaterialDTO> dtos = new ArrayList<>();
        for (Material material : materials) {
            dtos.add(materialToDTO(material));
        }
        return dtos;
    }
}
