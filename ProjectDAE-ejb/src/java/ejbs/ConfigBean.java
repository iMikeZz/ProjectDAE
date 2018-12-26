/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import dtos.AdministratorDTO;
import dtos.ClientDTO;
import dtos.ExtensionDTO;
import dtos.TemplateDTO;
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
    
    @EJB
    private TemplateBean templateBean;
    
    @EJB
    private SoftwareBean softwareBean;
    
    @EJB
    private ExtensionBean extensionBean;
    
    @PostConstruct
    public void populateDB() { 
        // Administrators
        administratorBean.create(new AdministratorDTO("b1", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("a1", "a1", "Alexandre", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("r3", "a1", "Joao", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("d1", "a1", "Pedro", "dae.ei.ipleiria@gmail.com", "Gestor"));
        
        // Clients
        clientBean.create(new ClientDTO("1234567", "Joao", "Leiria", "ADMIN", "AMC"));
        clientBean.create(new ClientDTO("c1", "c1", "Leiria", "ADMIN", "IPL"));
        clientBean.create(new ClientDTO("a123", "Zé", "Leiria", "ADMIN", "ABC"));
        
        softwareBean.create(1, "Sim", "2.0");
        softwareBean.create(2, "Nao", "2.0");
        softwareBean.create(3, "Batata", "2.0");
        
        templateBean.create(new TemplateDTO(71, "windows 98 pro", 1, "Sim")); //é necesserario passar o name pq estamos a usar o construtor do dto
        
        extensionBean.create(new ExtensionDTO(0, "gps", 1, 71));
    }
}
