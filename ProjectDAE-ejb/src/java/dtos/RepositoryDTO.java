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
@XmlRootElement(name = "Repository")
@XmlAccessorType(XmlAccessType.FIELD)
public class RepositoryDTO {
    private int id;
    private String link;
    private int config_id;
    
    public RepositoryDTO() {
    }

    public RepositoryDTO(int id, String link, int config_id) {
        this.id = id;
        this.link = link;
        this.config_id = config_id;
    }

    public RepositoryDTO(int id, String link) {
        this.id = id;
        this.link = link;
    }
    
    public void reset(){
        setId(0);
        setLink(null);
        setConfig_id(0);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        final RepositoryDTO other = (RepositoryDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
