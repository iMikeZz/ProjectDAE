/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import dtos.AdministratorDTO;
import dtos.ClientDTO;
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
        administratorBean.create(new AdministratorDTO("7894561", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("a1", "a1", "Manuel", "dae.ei.ipleiria@gmail.com", "Gestor"));
        
        // Clients
        clientBean.create(new ClientDTO("1234567", "Joao", "Leiria", "ADMIN", "IPL"));
        clientBean.create(new ClientDTO("c1", "c1", "Leiria", "ADMIN", "IPL"));
    }
}
