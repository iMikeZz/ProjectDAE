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
@XmlRootElement(name = "Extension")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtensionDTO {
    private int id;
    private String extension;
    private int software_id;

    public ExtensionDTO() {
    }

    public ExtensionDTO(int id, String extension, int software_id) {
        this.id = id;
        this.extension = extension;
        this.software_id = software_id;
    }
    
    public void reset(){
        setId(0);
        setExtension(null);
        setSoftware_id(0);
    }
    
    public int getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
