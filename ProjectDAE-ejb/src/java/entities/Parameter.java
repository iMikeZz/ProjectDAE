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
@Table(name = "PARAMETERS")
@NamedQueries({
    @NamedQuery(
            name = "getAllParameters",
            query = "SELECT t FROM Parameter t WHERE t.config IS NULL ORDER BY t.id"
    )
})
public class Parameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String parameter;
    private String value;
    
    @ManyToOne
    @JoinColumn(name = "CONFIG_ID")
    private ConfigBase config;

    public Parameter() {
    }

    public Parameter(int id, String parameter, String value, ConfigBase config) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
        this.config = config;
    }

    public Parameter(int id, String parameter, String value) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ConfigBase getConfig() {
        return config;
    }

    public void setConfig(ConfigBase config) {
        this.config = config;
    }
    
}
