/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Local;
import util.exception.EmployeeEntityException;

/**
 *
 * @author USER
 */
@Local
public interface EmployeeEntitySessionBeanLocal {
    public Employee login(String username, String password) throws EmployeeEntityException;
    public Employee retrieveById(Long id) throws EmployeeEntityException;
    public List<Employee> retrieveAllDoctors();

    public Employee retrieveByUsername(String username) throws EmployeeEntityException;

    public void updateEmployeeDetails(Employee employee) throws EmployeeEntityException;

    public void updateEmployeePassword(Employee employee) throws EmployeeEntityException;
}
