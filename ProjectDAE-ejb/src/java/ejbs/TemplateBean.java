/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ClientDTO;
import dtos.TemplateDTO;
import entities.Software;
import entities.Template;
import entities.roles.Client;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ruben Lauro
 */
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
    
    public TemplateDTO templateToDTO(Template template){
        return new TemplateDTO(template.getId(), template.getDescription(), template.getSoftware().getId());
    }
    
    
    public List<TemplateDTO> templatesToDTO(List<Template> templates){
        List<TemplateDTO> dtos = new ArrayList<>();
        for (Template template : templates) {
            dtos.add(templateToDTO(template));
        }
        return dtos;
    }
}
