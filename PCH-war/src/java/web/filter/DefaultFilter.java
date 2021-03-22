/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.filter;

import entity.Employee;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.enumeration.RoleEnum;

/**
 *
 * @author USER
 */
@WebFilter(filterName = "DefaultFilter", urlPatterns = {"/*"})
public class DefaultFilter implements Filter {
    
    FilterConfig filterConfig;
    
    private static final String CONTEXT_ROOT = "/PCH-war";

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        
//        System.out.println("---- DefaultFilter.doFilter()");
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String path = request.getServletPath();
        
        HttpSession session = request.getSession(true);
        
        if(session.getAttribute("isLogin") == null) {
            session.setAttribute("isLogin", false);
        }

        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        
        // Login page and other resources like images and CSS should be excluded from filters
        if (!path.equals("/login.xhtml") && !path.startsWith("/resources") && !path.startsWith("/javax.faces.resource") && !path.startsWith("/api")) {
            
            if (!isLogin) {
                response.sendRedirect(CONTEXT_ROOT + "/login.xhtml");
            } else {
                Employee employee = (Employee) session.getAttribute("user");
                
                /*
                ---- Filter individual pages example ----
                if(path.equals("/doctorpage.xhtml") || path.equals("/doctorpage2.xhtml")) {
                    if (employee.getRole().equals(RoleEnum.DOCTOR)) {
                        chain.doFilter(servletRequest, servletResponse);
                    } else {
                        response.sendRedirect(CONTEXT_ROOT + "/somewhere.xhtml");
                    }
                } else if(path.equals("/nursepage.xhtml")) {
                    if (employee.getRole().equals(RoleEnum.NURSE)) {
                        chain.doFilter(servletRequest, servletResponse);
                    } else {
                        response.sendRedirect(CONTEXT_ROOT + "/somewhere.xhtml");
                    }
                }
                */
                
                chain.doFilter(servletRequest, servletResponse);
            }
            
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
        
    }
    
}
