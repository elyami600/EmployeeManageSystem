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
    private  static EmployeeEmailValidator emailValidator;
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
            System.out.println("Please enter the Employee ID");
            int Id = sc.nextInt();

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

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid numeric Employee ID.");
            System.out.println(e);
            sc.nextLine();

        } catch (EmployeeNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + " Employee with ID " + e + " not found.");
            //throw new RuntimeException(e);
        }
    }
    private static void addEmployee () {
        try {
            System.out.println("Please enter employee ID:");
            int id;
            while (true) {
                System.out.println("Please enter employee ID (integer): ");
                if (sc.hasNextInt()) {
                    id = sc.nextInt();
                    sc.nextLine();
                    break;
                } else {
                    System.err.println("Error: Please enter a valid integer for ID.");
                    sc.nextLine();
                }
            }


            System.out.println("Please enter employee Department:");
            String department = sc.next();


            System.out.println("Please enter employee Name:");
            String name = sc.next();



            System.out.println("Please enter employee  Salary:");
            int salary;
            while (true) {
                System.out.println("Please enter employee ID (integer): ");
                if (sc.hasNextInt()) {
                    salary = sc.nextInt();
                    sc.nextLine();
                    break;
                } else {
                    System.err.println("Error: Please enter a valid integer for ID.");
                    sc.nextLine();
                }
            }

            System.out.println("Please enter employee Email:");
            String email;
            while(true) {
                email = sc.next();
                if(!emailValidator.isValidEmail(email)) {
                    System.err.println("Error: Invalid email format. Please enter invalid email");
                } else if(!emailValidator.addUniqueEmail(email)) {
                    System.err.println("Error: This email already exist in System. Please enter unique email");
                } else {
                    break;
                }
            }

            Employee employee = new Employee(id, name, department, salary, email);
            boolean addedSuccessfully =  manager.addEmployee(employee);

            if(addedSuccessfully) {
                System.out.println("Employ was add successful to the System");
                System.out.printf("Name: %s, Department: %s, Salary: %d, Email: %s%n",
                        employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getEmail());
            } else {
                System.out.printf("Employee with ID %d already exists in the system.%n", employee.getId());
            }
        } catch (InputMismatchException e) {
            System.out.println(e);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (EmployeeAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

   private   static  void updateEmployee() {
        try {
            System.out.println("Please enter employee ID ");

            if(!sc.hasNextInt()) {
                System.err.println("Error: Invalid input. Please enter a valid integer for the employee ID.");
                sc.next();
                return;
            }
            int id = sc.nextInt();
            Employee employee = manager.findEmployeeById(id);
            if(employee == null) {
                System.out.printf("Employee with ID %d was not found in the system.%n", employee.getId());
            }
            System.out.print("Enter new name (leave blank to keep current name: " + employee.getName() + "): ");
            String newName = sc.nextLine();
            if (!newName.trim().isEmpty()) {
                employee.setName(newName);
            }

            System.out.print("Enter new Department (leave blank to keep current name: " + employee.getDepartment()+ "): ");
            String newDepartment = sc.nextLine();
            if (!newDepartment.trim().isEmpty()) {
                employee.setDepartment(newDepartment);
            }
            System.out.print("Enter new Salary (leave blank to keep current name: " + employee.getSalary() + "): ");
            String newSalary = sc.nextLine();
            if (!newSalary.trim().isEmpty()) {
                employee.setSalary(Integer.parseInt(newSalary));
            }
            System.out.print("Enter new Email (leave blank to keep current name: " + employee.getEmail() + "): ");
            String newEmail = sc.nextLine();
            if (!newEmail.trim().isEmpty()) {
                employee.setEmail(newEmail);
            }
            boolean updatedEmployee = manager.updateEmployee(employee);
            if(updatedEmployee) {
                System.out.printf("Employee with ID %d was successful updated in the system.%n", employee.getId());
            } else {
                System.out.println("Error:  Failed to update Employee ID " + id);
            }

        } catch (InputMismatchException e)
        {
            System.out.println("Invalid input. Please enter a valid numeric Employee ID.");
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private static void removeEmployee() {
        try {
            System.out.println("Please enter employee ID ");

            if(!sc.hasNextInt()) {
                System.err.println("Error: Invalid input. Please enter a valid integer for the employee ID.");
                sc.next();
                return;
            }
            int id = sc.nextInt();

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
