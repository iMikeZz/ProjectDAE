/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ExtensionDTO;
import dtos.LicenseDTO;
import dtos.MaterialDTO;
import dtos.ModuleDTO;
import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import dtos.ServiceDTO;
import dtos.TemplateDTO;
import entities.ConfigBase;
import entities.Extension;
import entities.License;
import entities.Material;
import entities.Module;
import entities.Parameter;
import entities.Repository;
import entities.Service;
import entities.Software;
import entities.Template;
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
 * @author Ruben Lauro
 */
@DeclareRoles({"Administrator"})
@Stateless
@Path("/templates")
public class TemplateBean {
    @PersistenceContext
    EntityManager em;
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TemplateDTO> getAll(){
        try {
            List<Template> templates = em.createNamedQuery("getAllTemplates").getResultList();
            return templatesToDTO(templates);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("allOrderedByDescription")
    public List<TemplateDTO> getAllOrderedByDescription(){
        try {
            List<Template> templates = em.createNamedQuery("getAllTemplatesOrderedByDescription").getResultList();
            return templatesToDTO(templates);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{description}")
    public List<TemplateDTO> getAll(@PathParam("description") String description){
        try {
            List<Template> templates = em.createNamedQuery("getAllTemplatesByDescription")
                    .setParameter("description", "%" + description + "%")
                    .getResultList();
            return templatesToDTO(templates);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    //@RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(TemplateDTO templateDTO){
        try{
            Software software = em.find(Software.class, templateDTO.getSoftwareCode());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            ConfigBase template = new Template(templateDTO.getId(), templateDTO.getDescription(), software);
            software.addConfig(template);
            
            if (!templateDTO.getExtensions().isEmpty()) {
                for (ExtensionDTO extensionDTO : templateDTO.getExtensions()) {
                    Extension extension = em.find(Extension.class, extensionDTO.getId());
                    template.addExtension(extension);
                    extension.addConfig(template);
                }
            }
            if (!templateDTO.getLicenses().isEmpty()) {
                for (LicenseDTO licenseDTO : templateDTO.getLicenses()) {
                    License license = em.find(License.class, licenseDTO.getId());
                    template.addLicense(license);
                    license.addConfig(template);
                }
            }
            if (!templateDTO.getMaterials().isEmpty()) {
                for (MaterialDTO materialDTO : templateDTO.getMaterials()) {
                    Material material = em.find(Material.class, materialDTO.getId());
                    template.addMaterial(material);
                    material.setConfig(template);
                }
            }
            if (!templateDTO.getModules().isEmpty()) {
                for (ModuleDTO moduleDTO : templateDTO.getModules()) {
                    Module module = em.find(Module.class, moduleDTO.getId());
                    template.addModule(module);
                    module.addConfig(template);
                }
            }
            if (!templateDTO.getParameters().isEmpty()) {
                for (ParameterDTO parameterDTO : templateDTO.getParameters()) {
                    Parameter parameter = em.find(Parameter.class, parameterDTO.getId());
                    template.addParameter(parameter);
                    parameter.setConfig(template);
                }
            }
            if (!templateDTO.getRepositories().isEmpty()) {
                for (RepositoryDTO repositoryDTO : templateDTO.getRepositories()) {
                    Repository repository = em.find(Repository.class, repositoryDTO.getId());
                    template.addRepository(repository);
                    repository.setConfig(template);
                }
            }
            if (!templateDTO.getServices().isEmpty()) {
                for (ServiceDTO serviceDTO : templateDTO.getServices()) {
                    Service service = em.find(Service.class, serviceDTO.getId());
                    template.addService(service);
                    service.setConfig(template);
                }
            }
            em.persist(template);   
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    //@RolesAllowed({"Administrator"})
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(TemplateDTO templateDTO){
        try{
            Template template = (Template) em.find(Template.class, templateDTO.getId());
            if (template == null) {
                return;
            }
            Software software = em.find(Software.class, templateDTO.getSoftwareCode());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            template.setDescription(templateDTO.getDescription());
            template.setSoftware(software);
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
            Template template = (Template) em.find(Template.class, username);
            if (template == null) {
                return;
            }
            em.remove(template); 
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public TemplateDTO templateToDTO(Template template){
        return new TemplateDTO(template.getId(), template.getDescription(), template.getSoftware().getId(), template.getSoftware().getName());
    }
    
    
    public List<TemplateDTO> templatesToDTO(List<Template> templates){
        List<TemplateDTO> dtos = new ArrayList<>();
        for (Template template : templates) {
            dtos.add(templateToDTO(template));
        }
        return dtos;
    }
}
