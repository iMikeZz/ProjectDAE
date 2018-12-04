/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author Ruben Lauro
 */

public class UserDTO implements Serializable {
    protected String username;
    protected String password;   
    protected String name;

    

    public UserDTO() {
    }    
    
    public UserDTO(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
    
    public void reset() {
        setUsername(null);
        setPassword(null);
        setName(null);
    }        

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
