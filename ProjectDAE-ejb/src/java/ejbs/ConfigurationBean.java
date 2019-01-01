/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ConfigurationDTO;
import entities.ConfigBase;
import entities.Configuration;
import entities.Software;
import entities.roles.Client;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author migue
 */
@DeclareRoles({"Administrator"})
@Stateless
@Path("/configurations")
public class ConfigurationBean {
    @PersistenceContext
    EntityManager em;
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ConfigurationDTO> getAll(){
        try {
            List<Configuration> configurations = em.createNamedQuery("getAllConfigurations").getResultList();
            return configurationsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("client/{username}")
    public List<ConfigurationDTO> getAllByClient(@PathParam("username") String username){
        try {
            List<Configuration> configurations = em.createNamedQuery("getAllConfigurationsByClient")
                    .setParameter("username", username)
                    .getResultList();
            return configurationsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("client/{username}/software/{search}")
    public List<ConfigurationDTO> getBySoftwareByClient(@PathParam("username") String username, @PathParam("search") String search){
        try {
            List<Configuration> configurations = em.createNamedQuery("getConfigurationsBySoftwareByClient")
                    .setParameter("username", username)
                    .setParameter("search", "%" + search + "%")
                    .getResultList();
            return configurationsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("client/{username}/description/{search}")
    public List<ConfigurationDTO> getByDescriptionByClient(@PathParam("username") String username, @PathParam("search") String search){
        try {
            List<Configuration> configurations = em.createNamedQuery("getConfigurationsByDescriptionByClient")
                    .setParameter("username", username)
                    .setParameter("search", "%" + search + "%")
                    .getResultList();
            return configurationsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("client/{username}/state/{search}")
    public List<ConfigurationDTO> getAllByClient(@PathParam("username") String username, @PathParam("search") String search){
        try {
            List<Configuration> configurations = em.createNamedQuery("getConfigurationsByStateByClient")
                    .setParameter("username", username)
                    .setParameter("search", search)
                    .getResultList();
            return configurationsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{description}")
    public List<ConfigurationDTO> getAll(@PathParam("description") String description){
        try {
            List<Configuration> configurations = em.createNamedQuery("getAllConfigurationsByDescription")
                    .setParameter("description", "%" + description + "%")
                    .getResultList();
            return configurationsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ConfigurationDTO configurationDTO){
        try{
            Software software = em.find(Software.class, configurationDTO.getSoftwareCode());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            Client client = em.find(Client.class, configurationDTO.getClientUsername());
            if (client == null) {
                throw new EJBException("Client doesn't exists");
            }
            ConfigBase configuration = new Configuration(configurationDTO.getId(), client, configurationDTO.getDescription(), configurationDTO.getContractData(), configurationDTO.getState(), software);
            software.addConfig(configuration);
            em.persist(configuration);   
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(ConfigurationDTO configurationDTO){
        try{
            Configuration configuration = (Configuration) em.find(Configuration.class, configurationDTO.getId());
            if (configuration == null) {
                return;
            }
            Software software = em.find(Software.class, configurationDTO.getSoftwareCode());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            configuration.setDescription(configurationDTO.getDescription());
            configuration.setSoftware(software);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @DELETE
    //@RolesAllowed({"Administrator"})
    @Path("{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") int username){
        try{
            Configuration configuration = (Configuration) em.find(Configuration.class, username);
            if (configuration == null) {
                return;
            }
            em.remove(configuration); 
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public ConfigurationDTO configurationToDTO(Configuration configuration){
        return new ConfigurationDTO(configuration.getId(), configuration.getDescription(), configuration.getSoftware().getId(), configuration.getSoftware().getName(), configuration.getState(), configuration.getClient().getUsername(), configuration.getContractData());
    }
    
    
    public List<ConfigurationDTO> configurationsToDTO(List<Configuration> configurations){
        List<ConfigurationDTO> dtos = new ArrayList<>();
        for (Configuration configuration : configurations) {
            dtos.add(configurationToDTO(configuration));
        }
        return dtos;
    }
}
