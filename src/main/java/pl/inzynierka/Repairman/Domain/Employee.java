package pl.inzynierka.Repairman.Domain;

import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.User.Domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEmployee;
	private LocalDateTime dateHired;
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	@OneToMany(mappedBy = "repairman", cascade = CascadeType.ALL)
	private List<Maintenance> maintenances;

	public Employee() {
		maintenances = new ArrayList<>();
	}

	public Employee(User user) {
		this.dateHired = LocalDateTime.now();
		this.user = user;
		maintenances = new ArrayList<>();
	}

	public void assignMaintenance(Maintenance maintenance){
		maintenances.add(maintenance);
//		maintenance.takeMaintenance(this);
	}

	public Long getIdEmployee() {
		return idEmployee;
	}

	public LocalDateTime getDateHired() {
		return dateHired;
	}

	public User getUser() {
		return user;
	}

	public List<Maintenance> getMaintenances() {
		return maintenances;
	}

}
