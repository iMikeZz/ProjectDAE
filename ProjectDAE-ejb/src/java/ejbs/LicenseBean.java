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
@Path("/licenses")
public class LicenseBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    public void create(int id, String license, int software_id){
        try{
            Software software = em.find(Software.class, software_id);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            em.persist(new License(id, license, software));
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<LicenseDTO> getAll(){
        try {
            List<License> licenses = em.createNamedQuery("getAllLicenses").getResultList();
            return licensesToDTO(licenses);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public LicenseDTO licenseToDTO(License license){
        return new LicenseDTO(license.getId(), license.getLicense());
    }
    
    
    public List<LicenseDTO> licensesToDTO(List<License> licenses){
        List<LicenseDTO> dtos = new ArrayList<>();
        for (License license : licenses) {
            dtos.add(licenseToDTO(license));
        }
        return dtos;
    }
}
