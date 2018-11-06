/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;
 
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Logger;

@Named(value = "administratorManager")
@SessionScoped
public class AdministratorManager implements Serializable {
 
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    public AdministratorManager() {
    }
    
    public String login(){
        return "admin_index?faces-redirect=true";
    }
}
