/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package web;

import dtos.ExtensionDTO;
import dtos.LicenseDTO;
import dtos.MaterialDTO;
import dtos.ModuleDTO;
import dtos.ParameterDTO;
import dtos.RepositoryDTO;
import dtos.ServiceDTO;
import dtos.SoftwareDTO;
import dtos.TemplateDTO;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import static web.Manager.baseUri;

/**
 *
 * @author Ruben Lauro
 */
@ManagedBean(name = "configurationManager")
@SessionScoped
public class ConfigurationManager extends Manager implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ConfigurationManager.class.getName());
    
    private static final String SEARCHBYDESCRIPTION = "SEARCHCLIENTSBYNAME";
    private static final String ALLTEMPLATES = "ALLTEMPLATES";
    
    private TemplateDTO currentTemplate;
    private TemplateDTO newTemplate;
    
    private ExtensionDTO newExtension;
    private LicenseDTO newLicense;
    private MaterialDTO newMaterial;
    private ModuleDTO newModule;
    private ParameterDTO newParameter;
    private RepositoryDTO newRepository;
    private ServiceDTO newService;
    
    private int currentSoftwareId = 1;
    
    private String templatesVersion = ALLTEMPLATES;
    private String searchValue;
    
    public ConfigurationManager() {
        this.newTemplate = new TemplateDTO();
        
        this.newExtension = new ExtensionDTO();
        
        this.newMaterial = new MaterialDTO();
        
        this.newModule = new ModuleDTO();
        
        this.newParameter = new ParameterDTO();
        
        this.newRepository = new RepositoryDTO();
        
        this.newService = new ServiceDTO();
    }
    
    public TemplateDTO getCurrentTemplate() {
        return currentTemplate;
    }
    
    public void setCurrentTemplate(TemplateDTO currentTemplate) {
        this.currentTemplate = currentTemplate;
    }
    
    public TemplateDTO getNewTemplate() {
        return newTemplate;
    }
    
    public void setNewTemplate(TemplateDTO newTemplate) {
        this.newTemplate = newTemplate;
    }
    
    public String getTemplatesVersion() {
        return templatesVersion;
    }
    
    public void setTemplatesVersion(String templatesVersion) {
        this.templatesVersion = templatesVersion;
    }
    
    public String getSearchValue() {
        return searchValue;
    }
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    public String getSEARCHBYDESCRIPTION() {
        return SEARCHBYDESCRIPTION;
    }
    
    public String getALLTEMPLATES() {
        return ALLTEMPLATES;
    }
    
    public ExtensionDTO getNewExtension() {
        return newExtension;
    }
    
    public void setNewExtension(ExtensionDTO newExtension) {
        this.newExtension = newExtension;
    }
    
    public int getCurrentSoftwareId() {
        return currentSoftwareId;
    }
    
    public void setCurrentSoftwareId(int currentSoftwareId) {
        this.currentSoftwareId = currentSoftwareId;
    }
    
    public LicenseDTO getNewLicense() {
        return newLicense;
    }
    
    public void setNewLicense(LicenseDTO newLicense) {
        this.newLicense = newLicense;
    }
    
    public MaterialDTO getNewMaterial() {
        return newMaterial;
    }
    
    public void setNewMaterial(MaterialDTO newMaterial) {
        this.newMaterial = newMaterial;
    }
    
    public ModuleDTO getNewModule() {
        return newModule;
    }
    
    public void setNewModule(ModuleDTO newModule) {
        this.newModule = newModule;
    }
    
    public ParameterDTO getNewParameter() {
        return newParameter;
    }
    
    public void setNewParameter(ParameterDTO newParameter) {
        this.newParameter = newParameter;
    }
    
    public RepositoryDTO getNewRepository() {
        return newRepository;
    }
    
    public void setNewRepository(RepositoryDTO newRepository) {
        this.newRepository = newRepository;
    }
    
    public ServiceDTO getNewService() {
        return newService;
    }
    
    public void setNewService(ServiceDTO newService) {
        this.newService = newService;
    }
    
    
    //*******************TEMPLATES********************************
    public List<TemplateDTO> getAllTemplates(){
        try {
            switch (templatesVersion) {
                case ALLTEMPLATES:
                    return getTemplatesListByUrl("/templates/all");
                case SEARCHBYDESCRIPTION:
                    if (!searchValue.equals("")) {
                        return getTemplatesListByUrl("/templates/" + searchValue);
                    }
                    return getTemplatesListByUrl("/templates/all");
                default:
                    return null;
            }
            
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllTemplates", logger);
            return null;
        }
    }
    
    public void softwareChangeListener(ValueChangeEvent e){
        currentSoftwareId = Integer.parseInt(e.getNewValue().toString());
    }
    
    public List<ExtensionDTO> getAllTemplateExtensions(){
        try {
            return client.target(baseUri)
                    .path("/extensions/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<LicenseDTO> getAllTemplateLicenses(){
        try {
            return client.target(baseUri)
                    .path("/licenses/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<MaterialDTO> getAllTemplateMaterials(){
        try {
            return client.target(baseUri)
                    .path("/materials/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ModuleDTO> getAllTemplateModules(){
        try {
            return client.target(baseUri)
                    .path("/modules/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ModuleDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ParameterDTO> getAllTemplateParameters(){
        try {
            return client.target(baseUri)
                    .path("/parameters/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<RepositoryDTO> getAllTemplateRepositories(){
        try {
            return client.target(baseUri)
                    .path("/repositories/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ServiceDTO> getAllTemplateServices(){
        try {
            return client.target(baseUri)
                    .path("/services/" + currentTemplate.getId())
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<ExtensionDTO> getAllExtensions(){
        try {
            if (currentTemplate != null) {
                currentSoftwareId = currentTemplate.getSoftwareCode();
            }
            return client.target(baseUri)
                    .path("/extensions/all/" + currentSoftwareId)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ExtensionDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllExtensions", logger);
            return null;
        }
    }
    
    public List<LicenseDTO> getAllLicenses(){
        try {
            return client.target(baseUri)
                    .path("/licenses/all/" + currentSoftwareId)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<LicenseDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllLicenses", logger);
            return null;
        }
    }
    
    public List<MaterialDTO> getAllMaterials(){
        try {
            return client.target(baseUri)
                    .path("/materials/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<MaterialDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllMaterials", logger);
            return null;
        }
    }
    
    public List<ModuleDTO> getAllModules(){
        try {
            return client.target(baseUri)
                    .path("/modules/all/" + currentSoftwareId)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ModuleDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllModules", logger);
            return null;
        }
    }
    
    public List<ParameterDTO> getAllParameters(){
        try {
            return client.target(baseUri)
                    .path("/parameters/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ParameterDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllParameters", logger);
            return null;
        }
    }
    
    public List<RepositoryDTO> getAllRepositories(){
        try {
            return client.target(baseUri)
                    .path("/repositories/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<RepositoryDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllRepositories", logger);
            return null;
        }
    }
    
    public List<ServiceDTO> getAllServices(){
        try {
            return client.target(baseUri)
                    .path("/services/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ServiceDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllServices", logger);
            return null;
        }
    }
    
    public List<SoftwareDTO> getAllSoftwares(){
        try {
            return client.target(baseUri)
                    .path("/softwares/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<SoftwareDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem getting all templates in method getAllSoftwares", logger);
            return null;
        }
    }
    
    public String createTemplate() {
        try {
            client.target(baseUri)
                    .path("templates/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newTemplate));
            newTemplate.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createExtension() {
        try {
            newExtension.setSoftware_id(currentSoftwareId);
            client.target(baseUri)
                    .path("extensions/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newExtension));
            newExtension.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createLicense() {
        try {
            newLicense.setSoftware_id(currentSoftwareId);
            client.target(baseUri)
                    .path("licenses/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newLicense));
            newLicense.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createMaterial() {
        try {
            if (currentTemplate != null) {
                newMaterial.setConfig_id(currentTemplate.getId());
            }
            client.target(baseUri)
                    .path("materials/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newMaterial));
            newMaterial.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createModule() {
        try {
            newModule.setSoftware_id(currentSoftwareId);
            client.target(baseUri)
                    .path("materials/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newModule));
            newModule.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createParameter() {
        try {
            if (currentTemplate != null) {
                newParameter.setConfig_id(currentTemplate.getId());
            }
            client.target(baseUri)
                    .path("parameters/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newParameter));
            newParameter.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createRepository() {
        try {
            if (currentTemplate != null) {
                newRepository.setConfig_id(currentTemplate.getId());
            }
            client.target(baseUri)
                    .path("repositories/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newRepository));
            newRepository.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String createService() {
        try {
            if (currentTemplate != null) {
                newService.setConfig_id(currentTemplate.getId());
            }
            client.target(baseUri)
                    .path("service/create")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newService));
            newService.reset();
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        
        if (currentTemplate != null)
            return "/admin/templates/admin_template_update?faces-redirect=true";
        
        return "/admin/templates/admin_template_create?faces-redirect=true";
    }
    
    public String updateTemplate(){
        try {
            client.target(baseUri)
                    .path("templates/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTemplate));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem updating client in method updateClient", logger);
            return null;
        }
        return "/admin/admin_index?facelet-redirect=true";
    }
    
    public String removeTemplate(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteTemplateId");
            int username = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/templates/" + username)
                    .request(MediaType.APPLICATION_XML)
                    .delete(Boolean.class);
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing student in method removeTemplate ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String addExtension(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addExtensionId");
            int extension_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/extensions/add/"+ extension_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTemplate));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding extension in method addExtension ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeExtension(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeExtensionId");
            int extension_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/extensions/remove/"+ extension_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTemplate));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing extension in method removeExtension ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true";
    }
    
    public String addLicense(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/licenses/add/" + currentTemplate.getId() + "/" + license_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(Boolean.class));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding license in method addLicense ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeLicense(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeLicenseId");
            int license_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/licenses/remove/" + currentTemplate.getId() + "/" + license_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(Boolean.class));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem removing license in method removeLicense", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String addMaterial(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String removeMaterial(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String addModule(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("addModuleId");
            int module_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/modules/add/" + currentTemplate.getId() + "/" + module_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(Boolean.class));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding module in method addModule ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String removeModule(ActionEvent event){
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeModuleId");
            int module_id = Integer.parseInt(param.getValue().toString());
            client.target(baseUri)
                    .path("/modules/remove/" + currentTemplate.getId() + "/" + module_id)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(Boolean.class));
        }
        catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Problem adding module in method addModule ", logger);
            return null;
        }
        return "/admin/admin_index?faces-redirect=true"; //todo mudar
    }
    
    public String addParameter(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String removeParameter(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String addRepository(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String removeRepository(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String addService(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    public String removeService(ActionEvent event){
        throw new RuntimeException("Not suported yet.");
    }
    
    //**************COSTUM METHODS
    private List<TemplateDTO> getTemplatesListByUrl(String url){
        return client.target(baseUri)
                .path(url)
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<TemplateDTO>>() {
                });
    }
    
}
