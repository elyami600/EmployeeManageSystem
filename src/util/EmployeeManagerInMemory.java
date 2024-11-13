package util;

import Exeption.EmployeeNotFoundException;
import model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerInMemory implements  EmployeeManager {

    private static int idCounter = 1;
    private static List<Employee> employeeList = new ArrayList<Employee>();

    static {
        employeeList.add(new Employee(idCounter++, "Tom", "HR", 50000, "tom@email.com"));
        employeeList.add(new Employee(idCounter++, "Mary", "HR", 50000, "mary@email.com"));
        employeeList.add(new Employee(idCounter++, "Anna", "IT", 50000, "anna@email.com"));
    }
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
