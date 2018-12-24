/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author josea
 */
@Entity
@Table(name = "MATERIALS")
@NamedQueries({
    @NamedQuery(
            name = "getAllMaterials",
            query = "SELECT t FROM Material t ORDER BY t.id"
    )
})
public class Material implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private String imgUrl;
    
    @ManyToOne
    @JoinColumn(name = "CONFIG_ID")
    private ConfigBase config;
    
    public Material() {
    }

    public Material(int id, String description, String imgUrl, ConfigBase config) {
        this.id = id;
        this.description = description;
        this.imgUrl = imgUrl;
        this.config = config;
    }

    public ConfigBase getConfig() {
        return config;
    }

    public void setConfig(ConfigBase config) {
        this.config = config;
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
}
