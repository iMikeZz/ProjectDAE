/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author josea
 */
public class CommentDTO implements Serializable {
    
    private int id;
    private String content;
    private String date;
    private String user_name;
    private String userID;
    
    public CommentDTO() {
    }
    
    public CommentDTO(int id, String content, String date, String userid) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.userID = userid;
    }
    
    public void reset() {
        setContent("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }    
}