package pl.inzynierka.Equipment.Domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Image.Domain.Image;
import pl.inzynierka.Maintenance.Domain.Maintenance;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Equipment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idEquipment;
	private String name;
	private LocalDateTime dateCreation;
	@OneToOne
	private Image image;
	@ManyToOne
	private Client client;
	private Integer priority;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate lastMaintenanceDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfMinimumDurability;
	private Integer maintenanceFrequency;
	@Length(max = 1000)
	private String description;
	private String serialNumber;
	private LocalDate productionDate;
	@OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Maintenance> maintenances;

	public Equipment() {
	}

	public Equipment(String name, Image image, Client client, Integer priority, LocalDate lastMaintenanceDate, Integer maintenanceFrequency, String description, String serialNumber, LocalDate productionDate) {
		this.name = name;
		this.image = image;
		this.client = client;
		dateCreation = LocalDateTime.now();
		this.priority = priority;
		this.lastMaintenanceDate = lastMaintenanceDate;
		this.maintenanceFrequency = maintenanceFrequency;
		dateOfMinimumDurability = lastMaintenanceDate.plusDays(maintenanceFrequency);
		this.description = description;
		this.serialNumber = serialNumber;
		this.productionDate = productionDate;
	}

	public void changeInformation(String name, Image image, Client client, Integer priority, LocalDate lastMaintenanceDate, Integer maintenanceFrequency, String description, String serialNumber, LocalDate productionDate){
		if(name != null) this.name = name;
		if(image != null) this.image = image;
		if(client != null) this.client = client;
		if(priority != null) this.priority = priority;
		if(lastMaintenanceDate != null) this.lastMaintenanceDate = lastMaintenanceDate;
		if(maintenanceFrequency != null) this.maintenanceFrequency = maintenanceFrequency;
		if(description != null) this.description = description;
		if(serialNumber != null) this.serialNumber = serialNumber;
		if(productionDate != null) this.productionDate = productionDate;

	}

	public void updateLastMaintenanceDate(){
		this.lastMaintenanceDate = LocalDate.now();
		this.dateOfMinimumDurability = LocalDate.now().plusDays(maintenanceFrequency);
	}

	public void removeMaintenance(Maintenance maintenance){
		maintenances.remove(maintenance);
	}

	public Long getIdEquipment() {
		return idEquipment;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public List<Maintenance> getMaintenances() {
		return maintenances;
	}

	public Image getImage() {
		return image;
	}

	public Client getClient() {
		return client;
	}

	public Integer getPriority() {
		return priority;
	}

	public LocalDate getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	public Integer getMaintenanceFrequency() {
		return maintenanceFrequency;
	}

	public LocalDate getDateOfMinimumDurability() {
		return dateOfMinimumDurability;
	}

	public String getDescription() {
		return description;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public LocalDate getProductionDate() {
		return productionDate;
	}
}
