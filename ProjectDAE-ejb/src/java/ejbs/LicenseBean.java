/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.LicenseDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.License;
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
@Path("/licenses")
public class LicenseBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create/{id}")
    public void create(LicenseDTO licenseDTO, @PathParam("id") int config_id){
        try{
            Software software = em.find(Software.class, licenseDTO.getSoftware_id());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            License license = new License(licenseDTO.getId(), licenseDTO.getLicense(), software);
            ConfigBase configBase = em.find(ConfigBase.class, config_id);
            if (configBase == null) {
                software.addLicense(license);
                em.persist(license);
            } else{
                software.addLicense(license);
                license.addConfig(configBase);
                configBase.addLicense(license);
                em.persist(license);
            }
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
    public List<LicenseDTO> getAllByConfigBase(@PathParam("id") int id){
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
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{license_id}")
    public void addLicenseToTemplate(TemplateDTO templateDTO, @PathParam("license_id") int license_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            License license = em.find(License.class, license_id);
            if (license == null) {
                throw new EJBException("License doesn't exists");
            }
            
            if (configBase.getLicenses().contains(license)) {
                return;
            }
            
            if (license.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.addLicense(license);
            license.addConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{license_id}")
    public void removeLicenseFromTemplate(TemplateDTO templateDTO,@PathParam("license_id") int license_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            License license = em.find(License.class, license_id);
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
    @Path("licensesNotInTemplate/{id_config}/{id_software}")
    public List<LicenseDTO> getLicensesNotInTemplate(@PathParam("id_config")int id_config, @PathParam("id_software")int id_software) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Software software = em.find(Software.class, id_software);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            
            List<License> allLicenses = new ArrayList<>(software.getLicenses());
            
            allLicenses.removeAll(configBase.getLicenses());
            
            return licensesToDTO(allLicenses);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //************************CONFIGURATION*****************************
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToConfiguration/{license_id}")
    public void addLicenseToConfiguration(ConfigurationDTO configurationDTO, @PathParam("license_id") int license_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            License license = em.find(License.class, license_id);
            if (license == null) {
                throw new EJBException("License doesn't exists");
            }
            
            if (configBase.getLicenses().contains(license)) {
                return;
            }
            
            if (license.getConfigs().contains(configBase)) {
                return;
            }
            
            configBase.addLicense(license);
            license.addConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromConfiguration/{license_id}")
    public void removeLicenseFromConfiguration(ConfigurationDTO configurationDTO,@PathParam("license_id") int license_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            License license = em.find(License.class, license_id);
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
    @Path("licensesNotInConfiguration/{id_config}/{id_software}")
    public List<LicenseDTO> getLicensesNotInConfiguration(@PathParam("id_config")int id_config, @PathParam("id_software")int id_software) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Software software = em.find(Software.class, id_software);
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            
            List<License> allLicenses = new ArrayList<>(software.getLicenses());
            
            allLicenses.removeAll(configBase.getLicenses());
            
            return licensesToDTO(allLicenses);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    public static LicenseDTO licenseToDTO(License license){
        return new LicenseDTO(license.getId(), license.getLicense(), license.getSoftware().getId());
    }
    
    
    public static List<LicenseDTO> licensesToDTO(List<License> licenses){
        List<LicenseDTO> dtos = new ArrayList<>();
        for (License license : licenses) {
            dtos.add(licenseToDTO(license));
        }
        return dtos;
    }
}
