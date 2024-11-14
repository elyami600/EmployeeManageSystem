package util;

import java.util.Set;

public interface EmailValidator {

    boolean isValidEmail(String email);
    boolean addUniqueEmail(String email);
    Set<String> getUniqueEmails();
}
