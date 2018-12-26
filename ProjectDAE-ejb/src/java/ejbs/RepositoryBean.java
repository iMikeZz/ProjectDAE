/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import entities.ConfigBase;
import entities.Repository;
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
    public void create(int id, String link, int config_id){
        try{
            ConfigBase config = em.find(ConfigBase.class, config_id);
            if (config == null) {
                throw new EJBException("Config doesn't exists");
            }
            em.persist(new Repository(id, link, config));
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
    public List<RepositoryDTO> getAllByTemplate(@PathParam("id") int id){
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
    
    public RepositoryDTO repositoryToDTO(Repository repository){
        return new RepositoryDTO(repository.getId(), repository.getLink(), repository.getConfig().getId());
    }
    
    
    public List<RepositoryDTO> repositoriesToDTO(List<Repository> repositories){
        List<RepositoryDTO> dtos = new ArrayList<>();
        for (Repository repository : repositories) {
            dtos.add(repositoryToDTO(repository));
        }
        return dtos;
    }
}
