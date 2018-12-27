/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.LicenseDTO;
import entities.ConfigBase;
import entities.License;
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
@Path("/licenses")
public class LicenseBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(LicenseDTO licenseDTO){
        try{
            Software software = em.find(Software.class, licenseDTO.getSoftware_id());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            License license = new License(licenseDTO.getId(), licenseDTO.getLicense(), software);
            software.addLicense(license);
            em.persist(license);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all/{id}")
    public List<LicenseDTO> getAll(@PathParam("id") int id){
        try {
            Software software = em.find(Software.class, id);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            return licensesToDTO(software.getLicenses());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<LicenseDTO> getAllByTemplate(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return licensesToDTO(configBase.getLicenses());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public LicenseDTO licenseToDTO(License license){
        return new LicenseDTO(license.getId(), license.getLicense(), license.getSoftware().getId());
    }
    
    
    public List<LicenseDTO> licensesToDTO(List<License> licenses){
        List<LicenseDTO> dtos = new ArrayList<>();
        for (License license : licenses) {
            dtos.add(licenseToDTO(license));
        }
        return dtos;
    }
}
