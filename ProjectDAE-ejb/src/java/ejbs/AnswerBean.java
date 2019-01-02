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
@Path("/answers")
public class AnswerBean {
    @PersistenceContext
    EntityManager em;
    
    @GET
   // @RolesAllowed({"Client", "Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{question}")
    public List<AnswerDTO> getQuestionAnswers(@PathParam("question") int question){
        try {
            List<Answer> answers = em.createNamedQuery("getQuestionAnswers")
                        .setParameter("question", question)
                        .getResultList();
            return answersToDTO(answers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    //@RolesAllowed({"Client", "Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("create")
    public void create(AnswerDTO answerDTO){
        try{
            Question question = em.find(Question.class, answerDTO.getQuestion());
            if (question == null) {
                throw new EJBException("Question don't exist");
            }
            
            User user = em.find(User.class, answerDTO.getUserID());
            if (user == null) {
                throw new EJBException("User don't exist");
            }
            
            Answer answer = new Answer(answerDTO.getId(), answerDTO.getContent(), LocalDateTime.now().toString(), question, user);
            em.persist(answer);
        }catch(Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public AnswerDTO answerToDTO(Answer answer){
        AnswerDTO answerDTO = new AnswerDTO(answer.getId(), answer.getComment(), answer.getQuestion().getId(), answer.getUser().getUsername());
        answerDTO.setDate(answer.getDateTime());
        answerDTO.setUser_name(answer.getUser().getName());
        return answerDTO;
    }
    
    public List<AnswerDTO> answersToDTO(List<Answer> answers){
        List<AnswerDTO> dtos = new LinkedList<>();
        for (Answer answer : answers) {
            dtos.add(answerToDTO(answer));
        }
        return dtos;
    }
}
