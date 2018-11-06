/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import entities.roles.Administrator;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ruben Lauro
 */
@Stateless
public class AdministratorBean {
    
    @PersistenceContext
    EntityManager em;
    
    public void create(String username, String password, String name, String email, String role){
        try{
            Administrator administrator = new Administrator(username, password, name, email, role);
            em.persist(administrator);   
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public AdministratorDTO adminToDTO(Administrator admin){
        return new AdministratorDTO(admin.getUsername(), null, admin.getName(), admin.getEmail(), admin.getRole());
    }
    
    
    public List<AdministratorDTO> administratorsToDTO(List<Administrator> administrators){
        List<AdministratorDTO> dtos = new ArrayList<>();
        for (Administrator admin : administrators) {
            dtos.add(adminToDTO(admin));
        }
        return dtos;
    }
}