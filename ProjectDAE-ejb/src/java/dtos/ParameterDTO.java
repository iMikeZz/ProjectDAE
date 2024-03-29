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
@XmlRootElement(name = "Parameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParameterDTO {
    private int id;
    private String parameter;
    private String value;
    private int config_id;

    public ParameterDTO() {
    }

    public ParameterDTO(int id, String parameter, String value, int config_id) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
        this.config_id = config_id;
    }

    public ParameterDTO(int id, String parameter, String value) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
    }
    
    public void reset(){
        setId(0);
        setParameter(null);
        setValue(null);
        setConfig_id(0);
    }

    public int getConfig_id() {
        return config_id;
    }

    public void setConfig_id(int config_id) {
        this.config_id = config_id;
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
        final ParameterDTO other = (ParameterDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
