/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Software;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ruben Lauro
 */
@Stateless
@LocalBean
public class SoftwareBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    
    public void create(int id, String name, String version){
        try{
           em.persist(new Software(id, name, version));
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
}
