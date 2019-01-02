/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.AnswerDTO;
import dtos.QuestionDTO;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static web.Manager.baseUri;

/**
 *
 * @author josea
 */
@ManagedBean(name = "forumManager")
@SessionScoped
public class ForumManager extends Manager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.ForumManager");

    public QuestionDTO newQuestion;
    public AnswerDTO newAnswer;
    
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    public ForumManager() {
         this.newQuestion = new QuestionDTO();
         this.newAnswer = new AnswerDTO();
    }
    public List<AnswerDTO> getAllQuestionAnswers() {
        try {
            return client.target(baseUri)
                    .path("/answers/" + manager.currentQuestion.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<AnswerDTO>>() {});
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration questions in method getAllQuestionAnswers", logger);
            return null;
        }
    }
    
    public List<QuestionDTO> getAllConfigurationQuestions() {
        try {
            return client.target(baseUri)
                    .path("/questions/" + manager.currentConfiguration.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<QuestionDTO>>() {});
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting configuration questions in method getAllConfigurationQuestions", logger);
            return null;
        }
    }
    
    public String createQuestion() {
        try {
            newQuestion.setConfiguration(manager.currentConfiguration.getId());
            newQuestion.setUserID(userManager.getUsername());
            client.target(baseUri)
                    .path("questions/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newQuestion));
            newQuestion.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "client_config_questions?faces-redirect=true";
    }
    
    public String createAnswer(String filename) {
        try {
            newAnswer.setQuestion(manager.currentQuestion.getId());
            newAnswer.setUserID(userManager.getUsername());
            client.target(baseUri)
                    .path("answers/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newAnswer));
            newAnswer.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return filename + "?faces-redirect=true";
    }
    
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public QuestionDTO getNewQuestion() {
        return newQuestion;
    }

    public void setNewQuestion(QuestionDTO newQuestion) {
        this.newQuestion = newQuestion;
    }
    
    public AnswerDTO getNewAnswer() {
        return newAnswer;
    }

    public void setNewAnswer(AnswerDTO newAnswer) {
        this.newAnswer = newAnswer;
    }
}
