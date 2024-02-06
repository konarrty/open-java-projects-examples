package dto;

import java.io.Serializable;

public record EmployeeDTO(Long id, String firstName, String lastName, String middleName) implements Serializable {

    @Override
    public String toString() {
        return lastName + " " + firstName.charAt(0) + "." + middleName.charAt(0) + ".";
    }
}