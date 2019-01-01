/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ExtensionDTO;
import dtos.ServiceDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.Material;
import entities.Service;
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
@Path("/services")
public class ServiceBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ServiceDTO serviceDTO){
        try{
            ConfigBase config = em.find(ConfigBase.class, serviceDTO.getConfig_id());
            if (config == null) {
                em.persist(new Service(serviceDTO.getId(), serviceDTO.getService()));
            } else{
                Service service = new Service(serviceDTO.getId(), serviceDTO.getService(), config);
                config.addService(service);
                em.persist(service);
            }
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ServiceDTO> getAll(){
        try {
            List<Service> services = em.createNamedQuery("getAllServices").getResultList();
            return servicesToDTO(services);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ServiceDTO> getAllByConfigBase(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return servicesToDTO(configBase.getServices());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{service_id}")
    public void addServiceToTemplate(TemplateDTO templateDTO, @PathParam("service_id") int service_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Service service = em.find(Service.class, service_id);
            if (service == null) {
                throw new EJBException("Service doesn't exists");
            }
            
            if (configBase.getServices().contains(service)) {
                return;
            }
            
            configBase.addService(service);
            service.setConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{service_id}")
    public void removeServiceFromTemplate(TemplateDTO templateDTO,@PathParam("service_id") int service_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Service service = em.find(Service.class, service_id);
            if (service == null) {
                throw new EJBException("Service doesn't exists");
            }
            
            if (!configBase.getServices().contains(service)) {
                return;
            }
            
            configBase.removeService(service);
            service.setConfig(null);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("servicesNotInTemplate/{id_config}")
    public List<ServiceDTO> getRepositoriesNotInTemplate(@PathParam("id_config")int id_config) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            List<Service> allServices = em.createNamedQuery("getAllServices").getResultList();
            
            allServices.removeAll(configBase.getServices());
            
            return servicesToDTO(allServices);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ServiceDTO serviceToDTO(Service service){
        if (service.getConfig() != null) {
            return new ServiceDTO(service.getId(), service.getService(), service.getConfig().getId());
        }
        return new ServiceDTO(service.getId(), service.getService());
    }
    
    
    public List<ServiceDTO> servicesToDTO(List<Service> services){
        List<ServiceDTO> dtos = new ArrayList<>();
        for (Service service : services) {
            dtos.add(serviceToDTO(service));
        }
        return dtos;
    }
}
