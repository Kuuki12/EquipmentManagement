package pl.inzynierka.Report.Domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

@Entity
public class ReportOfMaintenance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idReport;
	private String name;
	private String description;
	private LocalDateTime dateCreation;
	private LocalDateTime dateLastModification;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] file;

	public ReportOfMaintenance() {
	}

	public ReportOfMaintenance(String name, String description, byte[] file) {
		this.name = name;
		this.description = description;
		this.file = file;
		dateCreation = LocalDateTime.now();
	}

	public void update(String name, String description, byte[] file){
		this.name = name;
		this.description = description;
		if(file != null) this.file = file;
		dateLastModification = LocalDateTime.now();
	}

	public Long getIdReport() {
		return idReport;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public LocalDateTime getDateLastModification() {
		return dateLastModification;
	}

	public byte[] getFile() {
		return file;
	}
}
