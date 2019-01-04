/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ConfigurationDTO;
import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Parameter;
import entities.Repository;
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
@Path("/repositories")
public class RepositoryBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(RepositoryDTO repositoryDTO){
        try{
            ConfigBase config = em.find(ConfigBase.class, repositoryDTO.getConfig_id());
            if (config == null) {
                em.persist(new Repository(repositoryDTO.getId(), repositoryDTO.getLink()));
            }else{
                Repository repository = new Repository(repositoryDTO.getId(), repositoryDTO.getLink(), config);
                config.addRepository(repository);
                repository.setConfig(config);
                em.persist(repository);
            }
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<RepositoryDTO> getAll(){
        try {
            List<Repository> repositories = em.createNamedQuery("getAllRepositories").getResultList();
            return repositoriesToDTO(repositories);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<RepositoryDTO> getAllByConfigBase(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return repositoriesToDTO(configBase.getRepositories());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{repository_id}")
    public void addRepositoryToTemplate(TemplateDTO templateDTO, @PathParam("repository_id") int repository_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Repository repository = em.find(Repository.class, repository_id);
            if (repository == null) {
                throw new EJBException("Repository doesn't exists");
            }
            
            if (configBase.getRepositories().contains(repository)) {
                return;
            }
            
            configBase.addRepository(repository);
            repository.setConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{repository_id}")
    public void removeRepositoryFromTemplate(TemplateDTO templateDTO,@PathParam("repository_id") int repository_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Repository repository = em.find(Repository.class, repository_id);
            if (repository == null) {
                throw new EJBException("Repository doesn't exists");
            }
            
            if (!configBase.getRepositories().contains(repository)) {
                return;
            }
            
            configBase.removeRepository(repository);
            repository.setConfig(null);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("repositoriesNotInTemplate/{id_config}")
    public List<RepositoryDTO> getRepositoriesNotInTemplate(@PathParam("id_config")int id_config) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            List<Repository> allRepositories = em.createNamedQuery("getAllRepositories").getResultList();
            
            allRepositories.removeAll(configBase.getRepositories());
            
            return repositoriesToDTO(allRepositories);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    //*************CONFIGURATION*******************   
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToConfiguration/{repository_id}")
    public void addRepositoryToConfiguration(ConfigurationDTO configurationDTO, @PathParam("repository_id") int repository_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Repository repository = em.find(Repository.class, repository_id);
            if (repository == null) {
                throw new EJBException("Repository doesn't exists");
            }
            
            if (configBase.getRepositories().contains(repository)) {
                return;
            }
            
            configBase.addRepository(repository);
            repository.setConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromConfiguration/{repository_id}")
    public void removeRepositoryFromConfiguration(ConfigurationDTO configurationDTO,@PathParam("repository_id") int repository_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, configurationDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Repository repository = em.find(Repository.class, repository_id);
            if (repository == null) {
                throw new EJBException("Repository doesn't exists");
            }
            
            if (!configBase.getRepositories().contains(repository)) {
                return;
            }
            
            configBase.removeRepository(repository);
            repository.setConfig(null);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("repositoriesNotInConfiguration/{id_config}")
    public List<RepositoryDTO> getRepositoriesNotInConfiguration(@PathParam("id_config")int id_config) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            List<Repository> allRepositories = em.createNamedQuery("getAllRepositories").getResultList();
            
            allRepositories.removeAll(configBase.getRepositories());
            
            return repositoriesToDTO(allRepositories);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public static RepositoryDTO repositoryToDTO(Repository repository){
        if (repository.getConfig() != null) {
            return new RepositoryDTO(repository.getId(), repository.getLink(), repository.getConfig().getId());
        }
        return new RepositoryDTO(repository.getId(), repository.getLink());
    }
    
    
    public static List<RepositoryDTO> repositoriesToDTO(List<Repository> repositories){
        List<RepositoryDTO> dtos = new ArrayList<>();
        for (Repository repository : repositories) {
            dtos.add(repositoryToDTO(repository));
        }
        return dtos;
    }
}
