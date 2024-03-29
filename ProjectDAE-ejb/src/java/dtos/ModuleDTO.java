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
@XmlRootElement(name = "Module")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModuleDTO {
    private int id;
    private String description;
    private int software_id;

    public ModuleDTO() {
        
    }

    public ModuleDTO(int id, String description, int software_id) {
        this.id = id;
        this.description = description;
        this.software_id = software_id;
    }
    
    public void reset(){
        setId(0);
        setDescription(null);
        setSoftware_id(0);
    }
    
    public int getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        final ModuleDTO other = (ModuleDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
