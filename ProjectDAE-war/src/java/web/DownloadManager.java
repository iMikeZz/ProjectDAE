package web;

import dtos.MaterialDTO;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.MediaType;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.URILookup;

@ManagedBean(name = "downloadManager")
@SessionScoped
public class DownloadManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.downloadManager");
    
    private int materialId;
   
    @ManagedProperty("#{manager}")
    protected Manager manager;
    
    @ManagedProperty("#{uploadManager}")
    protected UploadManager uploadManager;
    
    public DownloadManager() {
    }

    public StreamedContent getFile() {
        MaterialDTO material = null;
        try {
            material = manager.getClient().target(URILookup.getBaseAPI())
                    .path("/materials/material/" + materialId)
                    .request(MediaType.APPLICATION_XML)
                    .get(MaterialDTO.class);

            InputStream in = new FileInputStream(uploadManager.getCompletePathFile());
            
            String[] contents = material.getImgUrl().split("\\.");
            String mimeType = "application/"+contents[1];
            materialId = -1;
            return new DefaultStreamedContent(in, mimeType, material.getImgUrl());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Could not download the file", logger);
        }
        return null;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
}
