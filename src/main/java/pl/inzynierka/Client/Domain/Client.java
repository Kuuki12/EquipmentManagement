package pl.inzynierka.Client.Domain;

import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.User.Domain.User;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idClient;
	@OneToOne
    User user;
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	List<Equipment> equipments;
	
	public Client() {
	}

	public Client(User user, List<Equipment> equipments) {
		super();
		this.user = user;
		this.equipments = equipments;
	}
	
	public Long getIdClient() {
		return idClient;
	}
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Equipment> getEquipments() {
		return equipments;
	}
	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}
	
}
