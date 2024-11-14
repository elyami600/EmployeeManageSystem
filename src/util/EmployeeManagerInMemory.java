package util;

import Exeption.EmployeeAlreadyExistsException;
import Exeption.EmployeeNotFoundException;
import model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeManagerInMemory implements  EmployeeManager  {

    private static int idCounter = 1;
    private static List<Employee> employeeList = new ArrayList<Employee>();


    static {
        employeeList.add(new Employee(idCounter++, "Tom", "HR", 50000, "tom@email.com"));
        employeeList.add(new Employee(idCounter++, "Mary", "HR", 55000, "mary@email.com"));
        employeeList.add(new Employee(idCounter++, "Anna", "IT", 60000, "anna@email.com"));
        employeeList.add(new Employee(idCounter++, "John", "IT", 62000, "john@email.com"));
        employeeList.add(new Employee(idCounter++, "Jake", "Finance", 70000, "jake@email.com"));
        employeeList.add(new Employee(idCounter++, "Linda", "Finance", 73000, "linda@email.com"));
        employeeList.add(new Employee(idCounter++, "Robert", "Marketing", 52000, "robert@email.com"));
        employeeList.add(new Employee(idCounter++, "Patricia", "Marketing", 54000, "patricia@email.com"));
        employeeList.add(new Employee(idCounter++, "James", "Sales", 75000, "james@email.com"));
        employeeList.add(new Employee(idCounter++, "Jennifer", "Sales", 76000, "jennifer@email.com"));
        employeeList.add(new Employee(idCounter++, "Michael", "Admin", 48000, "michael@email.com"));
        employeeList.add(new Employee(idCounter++, "Sarah", "Admin", 46000, "sarah@email.com"));
    }
    @Override
    public List<Employee> getAllEmployees() {
       return  employeeList;
    }

    @Override
    public Employee findEmployeeById(int id) throws EmployeeNotFoundException {
        for(Employee employee : employeeList) {
            if(employee.getId() == id) {
                return  employee;
            }
        }
        throw new EmployeeNotFoundException(id);
    }

    private int findEmployee(Employee employee) {
        return employeeList.indexOf(employee);
    }
    @Override
    public boolean addEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        int position = findEmployee(employee);
        if(position >= 0) {
           throw new EmployeeAlreadyExistsException("Employee with ID " + employee.getId() + " already exists.");
        }
        employee.setId(idCounter++);
        employeeList.add(employee);
        return true;
    }

    @Override
    public boolean removeEmployeeById(int id) {
        return employeeList.removeIf(employee -> employee.getId() == id);

    }

    @Override
    public boolean updateEmployee(Employee employee) {
        if(employee == null) {
            System.err.println("Error the object is null");
            return false;
        }

        int position = findEmployee(employee);
        if(position < 0) {
            System.err.println("Employee with ID " + employee.getId() + " was not  found.");
            return  false;
        }
        employeeList.set(position, employee);
        System.out.println("Employee with ID " + employee.getId() + " was successfully updated.");
        return true;

    }

    @Override
    public List<Employee> getEmployeesByDepartment(String dept) {
       List<Employee> employees = employeeList.stream()
               .filter(employee -> employee.getDepartment().equals(dept))
               .collect(Collectors.toList());
       return employees;

    }


}
