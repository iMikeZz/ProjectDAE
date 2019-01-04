/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AnswerDTO;
import dtos.QuestionDTO;
import entities.Answer;
import entities.Configuration;
import entities.Question;
import entities.roles.User;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author josea
 */
@DeclareRoles({"Client", "Administrator"})
@Stateless
@Path("/questions")
public class QuestionBean {
    
    @PersistenceContext
    EntityManager em;
    
    @GET
    //@RolesAllowed({"Client","Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{configuration}")
    public List<QuestionDTO> getConfigurationQuestions(@PathParam("configuration") int config_id){
        try {
            List<Question> configurations = em.createNamedQuery("getConfigurationQuestions")
                                            .setParameter("configuration", config_id)
                                            .getResultList();
            return questionsToDTO(configurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    private int getQuestionAnswersCount(int question){
        try {
            return ((Long) em.createNamedQuery("getQuestionAnswersCount")
                                            .setParameter("question", question)
                                            .getSingleResult()).intValue();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    @RolesAllowed({"Client"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(QuestionDTO questionDTO){
        create_config(questionDTO);
    }
    
    public void create_config(QuestionDTO questionDTO){
        try{
            Configuration configuration = em.find(Configuration.class, questionDTO.getConfiguration());
            if (configuration == null) {
                throw new EJBException("Configuration doesn't exists");
            }
            
            User user = em.find(User.class, questionDTO.getUserID());
            if (user == null) {
                throw new EJBException("User don't exist");
            }
            
            Question question = new Question(questionDTO.getId(), questionDTO.getContent(), LocalDateTime.now().toString(), configuration, user);
            em.persist(question);
        }catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public QuestionDTO questionToDTO(Question question){
        QuestionDTO questionDTO = new QuestionDTO(question.getId(), question.getComment(),(question.getConfiguration()).getId(), question.getUser().getUsername());
        questionDTO.setAnswers(getQuestionAnswersCount(question.getId()));
        questionDTO.setUser_name(question.getUser().getName());
        questionDTO.setDate(question.getDateTime());
        return questionDTO;
    }
    
    public List<QuestionDTO> questionsToDTO(List<Question> questions){
        List<QuestionDTO> dtos = new LinkedList<>();
        for (Question question : questions) {
            dtos.add(questionToDTO(question));
        }
        return dtos;
    }
}
