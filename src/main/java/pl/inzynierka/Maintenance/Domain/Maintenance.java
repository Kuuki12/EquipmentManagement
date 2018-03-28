package pl.inzynierka.Maintenance.Domain;

import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Priority.PriorityStatus;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.Repairman.Domain.Employee;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Maintenance {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	@Enumerated(EnumType.STRING)
	private ProgressStatus progress;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateScheduled;
	private LocalDateTime dateTaken;
	private LocalDateTime dateDone;
	private LocalDateTime dateCreation;
	@OneToOne
	private ReportOfMaintenance report;
	@ManyToOne
	@JoinColumn(name="id_employee")
	private Employee repairman;
	@ManyToOne
	@JoinColumn(name="id_equipment")
	private Equipment equipment;
	@Enumerated(EnumType.STRING)
	private PriorityStatus priority;
	
	public Maintenance() {
	}

	public Maintenance(String name, LocalDate dateScheduled, Equipment equipment) {
		this.name = name;
		this.dateScheduled = dateScheduled;
		this.equipment = equipment;
		this.dateCreation = LocalDateTime.now();
		this.progress = ProgressStatus.WAITING;
		this.priority = PriorityStatus.VERY_LOW;
	}

	public void changeInformation(String name, LocalDate dateScheduled, Equipment equipment){
		if(name != null) this.name = name;
		if(dateScheduled != null) this.dateScheduled = dateScheduled;
		if(equipment != null) this.equipment = equipment;
	}

	public void changePriority(PriorityStatus newPriority){
		this.priority = newPriority;
	}

	public void takeMaintenance(Employee repairman){
//		repairman.assignMaintenance(this);
		this.repairman = repairman;
		dateTaken = LocalDateTime.now();
		progress = ProgressStatus.DOING;
	}

	public void doneMaintenance(ReportOfMaintenance report){
		this.progress = ProgressStatus.DONE;
		dateDone = LocalDateTime.now();
		this.report = report;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProgressStatus getProgress() {
		return progress;
	}

	public LocalDate getDateScheduled() {
		return dateScheduled;
	}

	public LocalDateTime getDateTaken() {
		return dateTaken;
	}

	public LocalDateTime getDateDone() {
		return dateDone;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public ReportOfMaintenance getReport() {
		return report;
	}

	public Employee getRepairman() {
		return repairman;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public PriorityStatus getPriority() {
		return priority;
	}
}
