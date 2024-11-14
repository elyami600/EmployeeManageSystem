package util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class EmployeeEmailValidator implements EmailValidator {

    private  static final Pattern EMAIL_PATTER = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static  Set<String> uniqueEmailList = new HashSet<>();

    @Override
    public boolean isValidEmail(String email) {
        return EMAIL_PATTER.matcher(email).matches();
    }

    @Override
    public boolean addUniqueEmail(String email) {
        if(!isValidEmail(email)) {
            System.err.println("Error: Invalid email format for " + email + ".");
            return  false;
        }
        return  uniqueEmailList.add(email);
    }

    @Override
    public Set<String> getUniqueEmails() {
        return uniqueEmailList;
    }
}
