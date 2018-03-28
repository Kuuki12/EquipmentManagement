package pl.inzynierka.Maintenance.Query;

import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.Equipment.Query.EquipmentQuery;

import java.time.LocalDate;

public class SelectedMaintenanceQuery {
    private Long id;
    private String name;
    private String emailOwner;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productionEquipment;
    private String serialNumberEquipment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfMinimumDurability;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledMaintenance;
    private String description;

    public SelectedMaintenanceQuery(Long id, String name, String emailOwner, LocalDate productionEquipment, String serialNumberEquipment, LocalDate dateOfMinimumDurability, LocalDate scheduledMaintenance, String description) {
        this.id = id;
        this.name = name;
        this.emailOwner = emailOwner;
        this.productionEquipment = productionEquipment;
        this.serialNumberEquipment = serialNumberEquipment;
        this.dateOfMinimumDurability = dateOfMinimumDurability;
        this.scheduledMaintenance = scheduledMaintenance;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailOwner() {
        return emailOwner;
    }

    public LocalDate getProductionEquipment() {
        return productionEquipment;
    }

    public String getSerialNumberEquipment() {
        return serialNumberEquipment;
    }

    public LocalDate getDateOfMinimumDurability() {
        return dateOfMinimumDurability;
    }

    public LocalDate getScheduledMaintenance() {
        return scheduledMaintenance;
    }

    public String getDescription() {
        return description;
    }
}
