/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.roles.User;
import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author josea
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "getConfigurationQuestions", 
            query = "Select q from Question q WHERE q.configuration.id = :configuration ORDER BY q.dateTime"
    ),
    @NamedQuery(
            name = "getQuestionAnswersCount", 
            query = "Select count(q) from Answer q WHERE q.question.id = :question"
    ),
})
public class Question extends Comment implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "CONFIG_CODE")
    private Configuration configuration;
            
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private LinkedList<Answer> answers;

    public Question() {
        this.answers = new LinkedList<>();
    }

    public Question(int id, String comment, String dateTime, Configuration configuration, User user) {
        super(id, comment, dateTime, user);
        this.answers = new LinkedList<>();
        this.configuration = configuration;
    }

    public LinkedList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(LinkedList<Answer> answers) {
        this.answers = answers;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
