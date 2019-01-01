/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.roles.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author josea
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "getQuestionAnswers", 
            query = "Select a from Answer a WHERE a.question.id = :question ORDER BY a.dateTime"
    )
})
public class Answer extends Comment implements Serializable {

    @ManyToOne
    @JoinColumn(name = "QUESTION_CODE")
    public Question question;

    public Answer() {
    }

    public Answer(int id, String comment, String dateTime, Question question, User user) {
        super(id, comment, dateTime, user);
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
