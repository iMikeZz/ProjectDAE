/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import dtos.AdministratorDTO;
import dtos.ClientDTO;
import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.MaterialDTO;
import dtos.TemplateDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import utils.State;

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
    
    @EJB
    private MaterialBean materialBean;
    
    @EJB
    private ConfigurationBean configurationBean;
            
    @PostConstruct
    public void populateDB() { 
        // Administrators
        administratorBean.create(new AdministratorDTO("b1", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("a1", "a1", "Alexandre", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("r3", "a1", "Joao", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("d1", "a1", "Pedro", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create(new AdministratorDTO("admin", "admin", "Pedro", "dae.ei.ipleiria@gmail.com", "Gestor"));
        
        // Clients
        clientBean.create(new ClientDTO("1234567", "Joao", "Leiria", "ADMIN", "AMC"));
        clientBean.create(new ClientDTO("c1", "c1", "Leiria", "ADMIN", "IPL"));
        clientBean.create(new ClientDTO("a123", "Zé", "Leiria", "ADMIN", "ABC"));
        
        softwareBean.create(1, "Sim", "2.0");
        softwareBean.create(2, "Nao", "2.0");
        softwareBean.create(3, "Batata", "2.0");
        
        TemplateDTO template1 = new TemplateDTO(71, "windows 98 pro", 1, "Sim");
        templateBean.create(template1); //é necesserario passar o name pq estamos a usar o construtor do dto
        
        extensionBean.create(new ExtensionDTO(1, "gps", 1), 71); //passar 0 se nao quisermos associar
        extensionBean.create(new ExtensionDTO(4, "gpstrack", 1), 0);
        extensionBean.create(new ExtensionDTO(2, "pokemon", 2), 0);
        extensionBean.create(new ExtensionDTO(3, "cenas", 3), 0);
        
        materialBean.create(new MaterialDTO(1, "sadsadasdsd", "sadasdsd"));
        
        ConfigurationDTO config1 = new ConfigurationDTO(2, "config1", 1, "Sim", "ACTIVE", "c1", "ContractDataAQUI");
        configurationBean.create(config1);
        
        //materialBean.addMaterialToTemplate(template1, 1);
        //materialBean.removeMaterialFromTemplate(template1, 1);
        //extensionBean.addExtensionToTemplate(, 1); //testado pela web
        //extensionBean.addExtensionToTemplate(71, 4);
        //extensionBean.removeExtensionToTemplate(71, 1);
    }
}
