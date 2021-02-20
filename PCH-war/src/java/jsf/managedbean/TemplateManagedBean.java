/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Medication;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author USER
 */
@Named(value = "templateManagedBean")
@RequestScoped
public class TemplateManagedBean {
    
    private static final String CONTEXT_ROOT = "/PCH-war";
    private String url;

    /**
     * Creates a new instance of TemplateManagedBean
     */
    public TemplateManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest servletRequest = (HttpServletRequest) ctx.getExternalContext().getRequest();
        // returns something like "/myapplication/home.faces"
        url = servletRequest.getRequestURI();
    }
    
    // Pass in one or more url where if matches any of them, will highlight the navigation element
    public String isUrl(String... pageNames) {
        for (String pageName : pageNames) {
            if (url.equals(CONTEXT_ROOT + pageName)) {
                return "selected";
            }
        }
        return "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
