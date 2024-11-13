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
    public boolean createEmployee(Employee empl) {
        return false;
    }

    @Override
    public boolean deleteEmployee(int id) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee empl) {
        return false;
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String dept) {
        return null;
    }
}
