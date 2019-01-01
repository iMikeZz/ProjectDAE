/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ruben Lauro
 */
@XmlRootElement(name = "Service")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceDTO {
    private int id;
    private String service;
    private int config_id;
    
    public ServiceDTO() {
    }

    public ServiceDTO(int id, String service, int config_id) {
        this.id = id;
        this.service = service;
        this.config_id = config_id;
    }

    public ServiceDTO(int id, String service) {
        this.id = id;
        this.service = service;
    }
    
    public void reset(){
        setId(0);
        setService(null);
        setConfig_id(0);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getConfig_id() {
        return config_id;
    }

    public void setConfig_id(int config_id) {
        this.config_id = config_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServiceDTO other = (ServiceDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
