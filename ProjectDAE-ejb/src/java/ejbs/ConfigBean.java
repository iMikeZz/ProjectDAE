/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 *
 * @author Ruben Lauro
 */
@Stateless
@Startup
public class ConfigBean {
    @EJB
    private AdministratorBean administratorBean;
    
    @EJB
    private ClientBean clientBean;
    
    @PostConstruct
    public void populateDB() { 
        administratorBean.create("7894561", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", "ADMIN");
        
        clientBean.create("1234567", "Joao", "Leiria", "ADMIN", "IPL");
    }
}
