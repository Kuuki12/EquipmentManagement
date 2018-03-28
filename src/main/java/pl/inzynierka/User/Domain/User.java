package pl.inzynierka.User.Domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Repairman.Domain.Employee;

@Entity(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUser;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private LocalDateTime dateCreation;
	private LocalDateTime dateLastModification;
	@Column(columnDefinition="boolean default true")
	private boolean enabled = true;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Employee employee;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Client client;
	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
	}

	public User(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = Role.ROLE_CLIENT;
		dateCreation = LocalDateTime.now();
		dateLastModification = LocalDateTime.now();
	}

	public User(String firstName, String lastName, String username, String password, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		dateCreation = LocalDateTime.now();
		dateLastModification = LocalDateTime.now();
	}

	public void changePassword(String newPassword){
		this.password = newPassword;
	}

	public void changePersonalSettings(String firstName, String lastName){

		if(firstName != null) this.firstName = firstName;
		if(lastName != null) this.lastName = lastName;
		dateLastModification = LocalDateTime.now();
	}

	public void changeRole(Role newRole){
		role = newRole;
	}

	public void changeStatus(boolean status){
		this.enabled = status;
	}

	public Long getIdUser() {
		return idUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public LocalDateTime getDateLastModification() {
		return dateLastModification;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Role getRole() {
		return role;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Client getClient() {
		return client;
	}
}
