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
 * @author migue
 */
@XmlRootElement(name = "Configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationDTO implements Serializable{
    private int id;
    private String description;
    private int softwareCode;
    private String softwareName;
    private String state;
    private String clientUsername;
    private String contractData;

    public ConfigurationDTO() {
    }

    public ConfigurationDTO(int id, String description, int softwareCode, String softwareName, String state, String clientUsername, String contractData) {
        this.id = id;
        this.description = description;
        this.softwareCode = softwareCode;
        this.softwareName = softwareName;
        this.state = state;
        this.clientUsername = clientUsername;
        this.contractData = contractData;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getContractData() {
        return contractData;
    }

    public void setContractData(String contractData) {
        this.contractData = contractData;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void reset(){
        setId(0);
        setDescription(null);
        setSoftwareCode(0);
        setSoftwareName(null);
        setState(null);
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
