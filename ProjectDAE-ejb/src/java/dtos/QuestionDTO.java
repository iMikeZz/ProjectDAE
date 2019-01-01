/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josea
 */
@XmlRootElement(name = "Question")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuestionDTO extends CommentDTO implements Serializable {
    
    private int answers;
    
    private int configuration;

    public QuestionDTO() {
    }

    public QuestionDTO(int id, String content, int configuration, String userid) {
        super(id, content, "0", userid);
        this.configuration = configuration;
    }    

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }
    
    public int getConfiguration() {
        return configuration;
    }

    public void setConfiguration(int configuration) {
        this.configuration = configuration;
    }
}

