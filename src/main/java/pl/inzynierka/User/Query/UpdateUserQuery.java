package pl.inzynierka.User.Query;

import pl.inzynierka.User.Domain.Role;

public class UpdateUserQuery {

    private String firstName;
    private String lastName;
    private Role role;

    public UpdateUserQuery(String firstName, String lastName, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

}