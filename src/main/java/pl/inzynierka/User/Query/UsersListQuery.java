package pl.inzynierka.User.Query;

import pl.inzynierka.User.Domain.Role;

import java.time.LocalDateTime;
import java.util.Date;

public class UsersListQuery {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private Role role;
    private LocalDateTime dateCreation;

    public UsersListQuery(Long id, String firstName, String lastName, String userName, Role role, LocalDateTime dateCreation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}

