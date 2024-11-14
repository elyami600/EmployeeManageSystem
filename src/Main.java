import Exeption.EmployeeAlreadyExistsException;
import Exeption.EmployeeNotFoundException;
import model.Employee;
import util.EmployeeEmailValidator;
import util.EmployeeManager;
import util.EmployeeManagerInMemory;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static EmployeeManager manager;
    private static EmployeeEmailValidator emailValidator;
    private static Scanner sc;

    public static void main(String[] args) {
        manager = new EmployeeManagerInMemory();
        //manager = new EmployeeManagerFile();

        sc = new Scanner(System.in);

        System.out.println("WELCOME TO THE EMPLOYEE MANAGEMENT SYSTEM (EMS)\n");

        mainMenu();


    }

    public static void mainMenu() {
        while (true) {
            System.out.println("\nPlease select one of the following options:"
                    + "\n1.) View Employees"
                    + "\n2.) Select Employee By ID"
                    + "\n3.) Create Employee"
                    + "\n4.) Update Employee"
                    + "\n5.) Delete Employee"
                    + "\n6.) Exit");

            int option = getValidOption(1,6);

            switch (option) {
                case 1 -> viewEmployees();
                case 2 -> getEmployeeByID();
                case 3 -> addEmployee();
                case 4 -> updateEmployee();
                case 5 ->removeEmployee();
                case 6 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                }
            }

        }

    }

    public static void viewEmployees() {


        while(true) {

            System.out.println("\nPlease select an option:"
                    + "\n1.) View All Employees"
                    + "\n2.) View Employees by Department"
                    + "\n3.) Return to Main Menu");

            int option = getValidOption(1,3);


            switch (option) {
                case 1 -> viewAllEmployees();
                case 2 -> viewEmployeeByDepartment();
                case 3 -> {
                    System.out.println("Returning to Main Menu.");
                    return;
                }
            }

        }

    }
    private static int getValidOption(int min, int max) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int option = sc.nextInt();
                sc.nextLine();

                if (option >= min && option <= max) {
                    return option;
                } else {
                    System.err.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.err.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            }
        }
    }

    private static int getValidatedInteger(String prompt) {
        int value;
        while (true) {
            System.out.println(prompt);
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.err.println("Error: Please enter a valid integer.");
                sc.nextLine();
            }
        }
        return value;
    }

    private static String getValidatedString(String prompt) {
        String value;
        while (true) {
            System.out.println(prompt);
            value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                break;
            } else {
                System.err.println("Error: Input cannot be empty. Please enter a valid value.");
            }
        }
        return value;
    }
    private static String getValidatedEmail(String prompt) {
        String email;
        while (true) {
            System.out.println(prompt);
            email = sc.nextLine().trim();
            if(!emailValidator.isValidEmail(email)) {
                System.err.println("Error: Invalid email format. Please enter invalid email");
            } else if(!emailValidator.addUniqueEmail(email)) {
                System.err.println("Error: This email already exist in System. Please enter unique email");
            } else if (!email.isEmpty())  {
                break;
            } else {
                System.err.println("Error: Input cannot be empty. Please enter a valid email.");
            }
        }
        return email;
    }
    private static void viewAllEmployees() {
        List<Employee> list = manager.getAllEmployees();
        if(list.isEmpty()) {
            System.out.println("No employees currently in the Employee Management System.");
        } else  {
            System.out.println("List of All Employees:");
            for(Employee employee : list) {
                System.out.println(employee);
            }
            System.out.println("Total Employees: " + list.size());
        }
    }
    private static void viewEmployeeByDepartment() {
        try {
            System.out.println("Please enter employee Department");
            String department = sc.next();

            List<Employee> employees = manager.getEmployeesByDepartment(department);
            if(employees.isEmpty()) {
                System.out.println("Employee system is Empty");
            } else {
                System.out.println("List of All Employees by Department:");
                for(Employee employee : employees) {
                    System.out.println(employee);
                }
                System.out.println("Total Employees: " + employees.size());
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid Department.");

        }

    }

   private static void getEmployeeByID() {
        try {
            int Id = getValidatedInteger("Please enter the Employee ID");

           Employee employee = manager.findEmployeeById(Id);
            System.out.println("=================================");
            System.out.println("         Employee Details        ");
            System.out.println("=================================");
            System.out.printf("Employee ID       : %d%n", employee.getId());
            System.out.printf("Employee Name     : %s%n", employee.getName());
            System.out.printf("Department        : %s%n", employee.getDepartment());
            System.out.printf("Salary            : $%s%n", employee.getSalary());
            System.out.printf("Email             : %s%n", employee.getEmail());
            System.out.println("=================================");

        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + " Employee with ID " + e + " not found.");
        }
    }
    private static void addEmployee () {

        try {
            int id = getValidatedInteger("Please enter employee ID (integer):");

            String department = getValidatedString ("Please enter employee Department:");

            String name = getValidatedString ("Please enter employee Name:");

            int salary =  getValidatedInteger("Please enter employee Salary (integer):");

            String email = getValidatedEmail("Please enter employee Email:");

            Employee employee = new Employee(id, name, department, salary, email);
            boolean addedSuccessfully =  manager.addEmployee(employee);

            if(addedSuccessfully) {
                System.out.println("Employ was add successful to the System");
                System.out.printf("Name: %s, Department: %s, Salary: %d, Email: %s%n",
                        employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getEmail());
            } else {
                System.out.printf("Employee with ID %d already exists in the system.%n", employee.getId());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EmployeeAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

   private   static  void updateEmployee() {
        try {

            int id = getValidatedInteger("Please enter employee ID (integer):");

            Employee employee = manager.findEmployeeById(id);
            if(employee == null) {
                System.out.printf("Employee with ID %d was not found in the system.%n", employee.getId());
            }

            String newName = getValidatedString("Enter new name (leave blank to keep current name: " + employee.getName() + "): ");
            if (!newName.trim().isEmpty()) {
                employee.setName(newName);
            }

            String newDepartment = getValidatedString("Enter new Department (leave blank to keep current name: " + employee.getDepartment()+ "): ");
            if (!newDepartment.trim().isEmpty()) {
                employee.setDepartment(newDepartment);
            }

            String newSalary = getValidatedInteger(("Enter new Salary (leave blank to keep current name: " + employee.getSalary() + "): ");)
            if (!newSalary.trim().isEmpty()) {
                employee.setSalary(Integer.parseInt(newSalary));
            }

            String newEmail = getValidatedEmail(("Enter new Email (leave blank to keep current name: " + employee.getEmail() + "): ");)
            if (!newEmail.trim().isEmpty()) {
                employee.setEmail(newEmail);
            }

            boolean updatedEmployee = manager.updateEmployee(employee);
            if(updatedEmployee) {
                System.out.printf("Employee with ID %d was successful updated in the system.%n", employee.getId());
            } else {
                System.out.println("Error:  Failed to update Employee ID " + id);
            }

        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private static void removeEmployee() {
        try {
            int id = getValidatedInteger("Please enter employee ID (integer):");

            boolean removedSuccessfully = manager.removeEmployeeById(id);

            if(removedSuccessfully) {
                System.out.printf("Employee with ID %d was removes from the  system.%n", id);
            } else {
                System.out.println("Error:  Employee with ID " + id + " not found.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Invalid input. Please enter a valid integer for the employee ID.");
        }

    }

}
