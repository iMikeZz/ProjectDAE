/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TemplateDTO;
import entities.Software;
import entities.Template;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed({"Administrator"})
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
    @RolesAllowed({"Administrator"})
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
            if (em.find(Template.class, templateDTO.getId()) != null) {
                throw new EJBException("Client already exists");
            }
            Software software = em.find(Software.class, templateDTO.getSoftwareCode());
            if (software == null) {
                throw new EJBException("Software doesn't exists");
            }
            em.persist(new Template(templateDTO.getId(), templateDTO.getDescription(), software));   
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
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
    @RolesAllowed({"Administrator"})
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
