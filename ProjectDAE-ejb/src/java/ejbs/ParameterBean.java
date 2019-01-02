/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejbs;

import dtos.ExtensionDTO;
import dtos.MaterialDTO;
import dtos.ParameterDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.Material;
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
                em.persist(new Parameter(parameterDTO.getId(), parameterDTO.getParameter(), parameterDTO.getValue()));
            }else{
                Parameter parameter = new Parameter(parameterDTO.getId(), parameterDTO.getParameter(), parameterDTO.getValue(), config);
                config.addParameter(parameter);
                parameter.setConfig(config);
                em.persist(parameter);
            }
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
    public List<ParameterDTO> getAllByConfigBase(@PathParam("id") int id){
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
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("addToTemplate/{parameter_id}")
    public void addParameterToTemplate(TemplateDTO templateDTO, @PathParam("parameter_id") int parameter_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Parameter parameter = em.find(Parameter.class, parameter_id);
            if (parameter == null) {
                throw new EJBException("Parameter doesn't exists");
            }
            
            if (configBase.getParameters().contains(parameter)) {
                return;
            }
            
            configBase.addParameter(parameter);
            parameter.setConfig(configBase);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("removeFromTemplate/{parameter_id}")
    public void removeParameterFromTemplate(TemplateDTO templateDTO,@PathParam("parameter_id") int parameter_id){
        try {
            ConfigBase configBase = em.find(ConfigBase.class, templateDTO.getId());
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            Parameter parameter = em.find(Parameter.class, parameter_id);
            if (parameter == null) {
                throw new EJBException("Parameter doesn't exists");
            }
            
            if (!configBase.getParameters().contains(parameter)) {
                return;
            }
            
            configBase.removeParameter(parameter);
            parameter.setConfig(null);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("parametersNotInTemplate/{id_config}")
    public List<ParameterDTO> getParametersNotInTemplate(@PathParam("id_config")int id_config) {
        try {
            ConfigBase configBase = em.find(ConfigBase.class, id_config);
            if (configBase == null) {
                throw new EJBException("Config doesn't exists");
            }
            
            List<Parameter> allParameters = em.createNamedQuery("getAllParameters").getResultList();
            
            allParameters.removeAll(configBase.getParameters());
            
            return parametersToDTO(allParameters);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public ParameterDTO parameterToDTO(Parameter parameter){
        if (parameter.getConfig() != null) {
            return new ParameterDTO(parameter.getId(), parameter.getParameter(), parameter.getValue(), parameter.getConfig().getId());
        }
        
        return new ParameterDTO(parameter.getId(), parameter.getParameter(), parameter.getValue());
    }
    
    
    public List<ParameterDTO> parametersToDTO(List<Parameter> parameters){
        List<ParameterDTO> dtos = new ArrayList<>();
        for (Parameter parameter : parameters) {
            dtos.add(parameterToDTO(parameter));
        }
        return dtos;
    }
}
