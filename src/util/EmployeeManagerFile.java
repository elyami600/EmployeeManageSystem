package util;

import Exeption.EmployeeNotFoundException;
import model.Employee;

import java.util.List;

public class EmployeeManagerFile implements EmployeeManager {

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Employee findEmployeeById(int id) throws EmployeeNotFoundException {
        return null;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean removeEmployeeById(int id) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return null;
    }
}