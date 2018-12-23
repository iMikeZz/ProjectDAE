/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ruben Lauro
 */
@XmlRootElement(name = "Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateDTO implements Serializable{
    
    private int id;
    private String description;
    private int softwareCode;
    private String softwareName;

    public TemplateDTO() {
    }

    public TemplateDTO(int id, String description, int softwareCode, String softwareName) {
        this.id = id;
        this.description = description;
        this.softwareCode = softwareCode;
        this.softwareName = softwareName;
    }

    public void reset(){
        setId(0);
        setDescription(null);
        setSoftwareCode(0);
        setSoftwareName(null);
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

    public int getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(int softwareCode) {
        this.softwareCode = softwareCode;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }
    
}
