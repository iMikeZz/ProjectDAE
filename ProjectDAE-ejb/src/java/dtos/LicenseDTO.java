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
@XmlRootElement(name = "License")
@XmlAccessorType(XmlAccessType.FIELD)
public class LicenseDTO {
    private int id;
    private String license;
    private int software_id;

    public LicenseDTO() {
        
    }

    public LicenseDTO(int id, String license, int software_id) {
        this.id = id;
        this.license = license;
        this.software_id = software_id;
    }
    
    public void reset(){
        setId(0);
        setLicense(null);
        setSoftware_id(0);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(int software_id) {
        this.software_id = software_id;
    }
}
