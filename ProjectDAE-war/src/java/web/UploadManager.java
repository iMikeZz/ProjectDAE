package web;

import dtos.MaterialDTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import org.primefaces.model.UploadedFile;
import util.URILookup;

@ManagedBean(name = "uploadManager")
@SessionScoped
public class UploadManager implements Serializable{

    private UploadedFile file;
    
    private String completePathFile;
    private String filename;
    
    private MaterialDTO currentMaterial;
    
    @ManagedProperty("#{manager}")
    private Manager manager;

    public UploadManager() {
    }

    public void upload(ActionEvent event) {
        if (file != null) {
            try {
                filename = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);

                completePathFile = URILookup.getServerDocumentsFolder() + filename;
                
                InputStream in = file.getInputstream();
                FileOutputStream out = new FileOutputStream(completePathFile);

                byte[] b = new byte[1024];
                int readBytes = in.read(b);

                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }
                in.close();
                out.close();
                UIParameter param = (UIParameter) event.getComponent().findComponent("materialToUpdate");
                MaterialDTO materialDTO = (MaterialDTO)param.getValue();
                materialDTO.setImgUrl(filename);
                manager.getClient().target(URILookup.getBaseAPI())
                    .path("/materials/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(materialDTO));
                FacesMessage message = new FacesMessage("File: " + file.getFileName() + " uploaded successfully!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (IOException e) {
                FacesMessage message = new FacesMessage("ERROR :: File: " + file.getFileName() + " not uploaded!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCompletePathFile() {
        return completePathFile;
    }

    public void setCompletePathFile(String completePathFile) {
        this.completePathFile = completePathFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public MaterialDTO getCurrentMaterial() {
        return currentMaterial;
    }

    public void setCurrentMaterial(MaterialDTO currentMaterial) {
        this.currentMaterial = currentMaterial;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
