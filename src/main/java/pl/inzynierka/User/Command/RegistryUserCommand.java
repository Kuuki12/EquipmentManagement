package pl.inzynierka.User.Command;

import org.hibernate.validator.constraints.NotEmpty;
import pl.inzynierka.User.Domain.Role;

import javax.validation.constraints.Size;

public class RegistryUserCommand {

    @NotEmpty(message = "Pole nie może być puste")
    private String email;
    @NotEmpty(message = "Pole nie może być puste")
    @Size(min = 8, message = "Wymagane jest minimum 8 znaków")
    private String password;
    @NotEmpty(message = "Pole nie może być puste")
    private String firstName;
    @NotEmpty(message = "Pole nie może być puste")
    private String lastName;

    public RegistryUserCommand() {
    }

    public RegistryUserCommand(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}