package pl.inzynierka.User.Command;

import org.hibernate.validator.constraints.NotEmpty;
import pl.inzynierka.User.Domain.Role;

public class UpdateUserCommand {

    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private Role role;

    public UpdateUserCommand() {
    }

    public UpdateUserCommand(Long id, String firstName, String lastName, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
