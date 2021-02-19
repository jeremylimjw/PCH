/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EmployeeEntityException;

/**
 *
 * @author USER
 */
@Stateless
public class EmployeeEntitySessionBean implements EmployeeEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    @Override
    public Employee login(String username, String password) throws EmployeeEntityException {
        Employee employee = retrieveByUsername(username);
        
        if (employee.getPassword().equals(password)) {
            em.detach(employee);
            employee.setPassword("");
            return employee;
        } else {
            throw new EmployeeEntityException("Invalid password!");
        }
    }
    
    public Employee retrieveByUsername(String username) throws EmployeeEntityException {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = ?1");
        query.setParameter(1, username);

        try {
            return (Employee)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException e) {
            throw new EmployeeEntityException("Username " + username + " does not exist!");
        }
    }
}
