package util;

import Exeption.EmployeeAlreadyExistsException;
import Exeption.EmployeeNotFoundException;
import model.Employee;

import java.util.List;

public interface EmployeeManager {
    List<Employee> getAllEmployees();

     Employee findEmployeeById(int id) throws EmployeeNotFoundException;

    boolean addEmployee(Employee employee) throws EmployeeAlreadyExistsException;

    boolean removeEmployeeById(int id);

    boolean updateEmployee(Employee employee);

     List<Employee> getEmployeesByDepartment(String department);


   // List<Employee> searchEmployees(EmployeeSearchCriteria criteria);
}

