/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ModuleDTO;
import dtos.ParameterDTO;
import entities.ConfigBase;
import entities.Parameter;
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
@Path("/parameters")
public class ParameterBean {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
            EntityManager em;
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(ParameterDTO parameterDTO){
        try{
            ConfigBase config = em.find(ConfigBase.class, parameterDTO.getConfig_id());
            if (config == null) {
                throw new EJBException("Config doesn't exists");
            }
            em.persist(new Parameter(parameterDTO.getId(), parameterDTO.getParameter(), parameterDTO.getValue(), config));
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ParameterDTO> getAll(){
        try {
            List<Parameter> parameters = em.createNamedQuery("getAllParameters").getResultList();
            return parametersToDTO(parameters);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public List<ParameterDTO> getAllByTemplate(@PathParam("id") int id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            return parametersToDTO(configBase.getParameters());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ParameterDTO parameterToDTO(Parameter parameter){
        return new ParameterDTO(parameter.getId(), parameter.getParameter(), parameter.getValue(), parameter.getConfig().getId());
    }
    
    
    public List<ParameterDTO> parametersToDTO(List<Parameter> parameters){
        List<ParameterDTO> dtos = new ArrayList<>();
        for (Parameter parameter : parameters) {
            dtos.add(parameterToDTO(parameter));
        }
        return dtos;
    }
}
