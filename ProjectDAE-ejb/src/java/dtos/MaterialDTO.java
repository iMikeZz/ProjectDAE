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
@XmlRootElement(name = "Material")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterialDTO {
    private int id;
    private String description;
    private String imgUrl;
    private int config_id;
    
    public MaterialDTO() {
    }

    public MaterialDTO(int id, String description, String imgUrl, int config_id) {
        this.id = id;
        this.description = description;
        this.imgUrl = imgUrl;
        this.config_id = config_id;
    }

    public MaterialDTO(int id, String description, String imgUrl) {
        this.id = id;
        this.description = description;
        this.imgUrl = imgUrl;
    }
    
    public void reset(){
        setId(0);
        setDescription(null);
        setImgUrl(null);
        setConfig_id(0);
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getConfig_id() {
        return config_id;
    }

    public void setConfig_id(int config_id) {
        this.config_id = config_id;
    }
    
}
