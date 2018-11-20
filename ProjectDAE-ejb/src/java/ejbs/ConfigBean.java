/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Ruben Lauro
 */
@Singleton
@Startup
public class ConfigBean {
    @EJB
    private AdministratorBean administratorBean;
    
    @EJB
    private ClientBean clientBean;
    
    @PostConstruct
    public void populateDB() { 
        // Administrators
        administratorBean.create("7894561", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", "Gestor");
        
        // Clients
        clientBean.create("1234567", "Joao", "Leiria", "ADMIN", "IPL");
    }
}
