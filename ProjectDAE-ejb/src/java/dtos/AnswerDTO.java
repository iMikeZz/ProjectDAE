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
@XmlRootElement(name = "Answer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnswerDTO extends CommentDTO implements Serializable {

    private int question;
    
    public AnswerDTO() {
    }

    public AnswerDTO(int id, String content, int question, String userid) {
        super(id, content, "0", userid);
        this.question = question;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }
}
