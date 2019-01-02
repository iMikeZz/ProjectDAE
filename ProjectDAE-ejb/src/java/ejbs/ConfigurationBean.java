/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.LicenseDTO;
import dtos.MaterialDTO;
import dtos.ModuleDTO;
import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import dtos.ServiceDTO;
import entities.ConfigBase;
import entities.Configuration;
import entities.Extension;
import entities.License;
import entities.Material;
import entities.Module;
import entities.Parameter;
import entities.Repository;
import entities.Service;
import entities.Software;
import entities.roles.Client;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import utils.State;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
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
    
    @EJB
    EmailBean emailBean;
    
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
            
            if (!configurationDTO.getExtensions().isEmpty()) {
                for (ExtensionDTO extensionDTO : configurationDTO.getExtensions()) {
                    Extension extension = em.find(Extension.class, extensionDTO.getId());
                    configuration.addExtension(extension);
                    extension.addConfig(configuration);
                }
            }
            if (!configurationDTO.getLicenses().isEmpty()) {
                for (LicenseDTO licenseDTO : configurationDTO.getLicenses()) {
                    License license = em.find(License.class, licenseDTO.getId());
                    configuration.addLicense(license);
                    license.addConfig(configuration);
                }
            }
            if (!configurationDTO.getMaterials().isEmpty()) {
                for (MaterialDTO materialDTO : configurationDTO.getMaterials()) {
                    Material material = em.find(Material.class, materialDTO.getId());
                    configuration.addMaterial(material);
                    material.setConfig(configuration);
                }
            }
            if (!configurationDTO.getModules().isEmpty()) {
                for (ModuleDTO moduleDTO : configurationDTO.getModules()) {
                    Module module = em.find(Module.class, moduleDTO.getId());
                    configuration.addModule(module);
                    module.addConfig(configuration);
                }
            }
            if (!configurationDTO.getParameters().isEmpty()) {
                for (ParameterDTO parameterDTO : configurationDTO.getParameters()) {
                    Parameter parameter = em.find(Parameter.class, parameterDTO.getId());
                    configuration.addParameter(parameter);
                    parameter.setConfig(configuration);
                }
            }
            if (!configurationDTO.getRepositories().isEmpty()) {
                for (RepositoryDTO repositoryDTO : configurationDTO.getRepositories()) {
                    Repository repository = em.find(Repository.class, repositoryDTO.getId());
                    configuration.addRepository(repository);
                    repository.setConfig(configuration);
                }
            }
            if (!configurationDTO.getServices().isEmpty()) {
                for (ServiceDTO serviceDTO : configurationDTO.getServices()) {
                    Service service = em.find(Service.class, serviceDTO.getId());
                    configuration.addService(service);
                    service.setConfig(configuration);
                }
            }
            
            em.persist(configuration);   
            
            /*
            // Email to client
            emailBean.send(
                    "miguelsousa.14@gmail.com",
                    "Configuration Created",
                    mailTemplateConfigurationCreated(configuration, "created")
            );
            */
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
            Client client = em.find(Client.class, configurationDTO.getClientUsername());
            if (client == null) {
                throw new EJBException("Client doesn't exists");
            }
            configuration.setDescription(configurationDTO.getDescription());
            configuration.setSoftware(software);
            configuration.setClient(client);
            client.addConfig(configuration);
            configuration.setContractData(configurationDTO.getContractData());
            configuration.setState(configurationDTO.getState());
            
            
            /*
            // Email to client
            emailBean.send(
                    "miguelsousa.14@gmail.com",
                    "Configuration Updated",
                    mailTemplateConfigurationCreated(configuration, "updated")
            );
            */
            
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
            
            /*
            // Email to client
            emailBean.send(
                    "miguelsousa.14@gmail.com",
                    "Configuration " + configuration.getDescription() + " removed",
                    mailTemplateConfigurationCreated(configuration, "removed")
            );
            */
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
    
    private String mailTemplateConfigurationCreated(ConfigBase configuration, String type) {

        LocalDateTime date = LocalDateTime.now();

        StringBuilder sb = new StringBuilder();
        sb.append("We would like to inform you that the following "
                + "configuration was " +  type + "\n\n "
                + "Details \n\n"
                + "Date: " + date.format(RFC_1123_DATE_TIME) + "\n"
                + "Description: " + configuration.getDescription() + "\n"
                + "Software Name: " + configuration.getSoftware().getName() + "\n"  
                + "Software Version: " + configuration.getSoftware().getVersion() + "\n"
                + "\n\n");
                
        return sb.toString();
    }
}
