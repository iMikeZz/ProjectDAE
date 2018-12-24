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
@Path("/extensions")
public class ExtensionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    public void create(int id, String extention, int software_id){
        try{
            Software software = em.find(Software.class, software_id);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
           em.persist(new Extension(id, extention, software));
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
            return softwaresToDTO(extensions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ExtensionDTO extensionToDTO(Extension extension){
        return new ExtensionDTO(extension.getId(), extension.getExtension());
    }
    
    
    public List<ExtensionDTO> softwaresToDTO(List<Extension> extensions){
        List<ExtensionDTO> dtos = new ArrayList<>();
        for (Extension extension : extensions) {
            dtos.add(extensionToDTO(extension));
        }
        return dtos;
    }
}
