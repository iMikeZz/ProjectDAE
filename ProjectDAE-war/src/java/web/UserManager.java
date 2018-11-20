/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author josea
 */
@Named(value = "userManager")
@SessionScoped
public class UserManager implements Serializable{

    private static final Logger logger = Logger.getLogger("web.UserManager");
    
    private boolean loginFlag = true;
    private String username;
    private String password;

    public UserManager() {
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
        } catch (ServletException e) {
            logger.log(Level.WARNING, e.getMessage());
            return "error_login?faces-redirect=true";
        }
        if (isUserInRole("Administrator")) {
            return "/faces/admin/admin_index?faces-redirect=true";
        }
        if (isUserInRole("Client")) {
            return "/faces/client/client_index?faces-redirect=true";
        }
        
        return "error_login?faces-redirect=true";
    }
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();

        for (String bean : context.getExternalContext().getSessionMap().keySet()) {
            context.getExternalContext().getSessionMap().remove(bean);
        }
        
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        // using faces-redirect to initiate a new request:
        return "/index.xhtml?faces-redirect=true";
    }
    
    public String clearLogin() {
        if (isSomeUserAuthenticated()) {
            logout();
        }
        return "index_login.xhtml?faces-redirect=true";
    }
    
    public boolean isUserInRole(String role) {
        return (isSomeUserAuthenticated() && FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role));
    }

    public boolean isSomeUserAuthenticated() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }
    
    public boolean isLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(boolean loginFlag) {
        this.loginFlag = loginFlag;
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
}
