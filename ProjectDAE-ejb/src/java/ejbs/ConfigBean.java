/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import dtos.AdministratorDTO;
import dtos.AnswerDTO;
import dtos.ClientDTO;
import dtos.ConfigurationDTO;
import dtos.ExtensionDTO;
import dtos.MaterialDTO;
import dtos.QuestionDTO;
import dtos.TemplateDTO;
import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Ruben Lauro
 */
@Singleton
@Startup
@RunAs(value = "Administrator")
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
     
    @EJB
    private QuestionBean questionBean;
    
    @EJB
    private AnswerBean answerBean;
    
    @PostConstruct
    public void populateDB() { 
        // Administrators
     
        administratorBean.create_config(new AdministratorDTO("b1", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create_config(new AdministratorDTO("a1", "a1", "Alexandre", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create_config(new AdministratorDTO("r3", "a1", "Joao", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create_config(new AdministratorDTO("d1", "a1", "Pedro", "dae.ei.ipleiria@gmail.com", "Gestor"));
        administratorBean.create_config(new AdministratorDTO("admin", "admin", "Pedro", "dae.ei.ipleiria@gmail.com", "Gestor"));
        
        // Clients
        clientBean.create_config(new ClientDTO("1234567", "Joao", "Leiria", "ADMIN", "AMC", "miguel.angelo_14@hotmail.com"));
        clientBean.create_config(new ClientDTO("c1", "c1", "Leiria", "ADMIN", "IPL", "miguel.angelo_14@hotmail.com"));
        clientBean.create_config(new ClientDTO("a123", "Zé", "Leiria", "ADMIN", "ABC", "miguel.angelo_14@hotmail.com"));
        
        softwareBean.create(1, "Primavera", "2.0");
        softwareBean.create(2, "Wintouch", "2.0");
        softwareBean.create(3, "Zonesoft", "2.0");
        
        TemplateDTO template1 = new TemplateDTO(71, "Windows 98 pro", 1, "Sim");
        TemplateDTO template2 = new TemplateDTO(72, "Windows XP", 1, "Sim");
        templateBean.create_config(template1); //é necesserario passar o name pq estamos a usar o construtor do dto
        templateBean.create_config(template2);
        
        extensionBean.create_config(new ExtensionDTO(1, "Gps", 1), 0); //passar 0 se nao quisermos associar
        extensionBean.create_config(new ExtensionDTO(4, "Gpstrack", 1), 0);
        extensionBean.create_config(new ExtensionDTO(2, "GeoMap", 2), 0);
        extensionBean.create_config(new ExtensionDTO(3, "TerrainMap", 3), 0);
        
        materialBean.create_config(new MaterialDTO(1, "New Material", null));
        
        ConfigurationDTO config1 = new ConfigurationDTO(2, "First Configuration", 1, "Primavera", "ACTIVE", "c1", "Contract Data");
        configurationBean.create_config(config1);
        
        questionBean.create_config(new QuestionDTO(1, "Is there any tutorial for my configuration?", config1.getId(), "1234567"));
        answerBean.create_config(new AnswerDTO(1, "It will be there soon on your available materials", 1, "1234567"));
        
        //materialBean.addMaterialToTemplate(template1, 1);
        //materialBean.removeMaterialFromTemplate(template1, 1);
        //extensionBean.addExtensionToTemplate(, 1); //testado pela web
        //extensionBean.addExtensionToTemplate(71, 4);
        //extensionBean.removeExtensionToTemplate(71, 1);
    }
}
