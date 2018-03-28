package pl.inzynierka.Maintenance.Query;

import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.Equipment.Query.EquipmentQuery;

import java.time.LocalDate;

public class MaintenanceQuery {
    private Long id;
    private String name;
    private EquipmentQuery equipment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledMaintenance;

    public MaintenanceQuery(Long id, String name, LocalDate scheduledMaintenanceDate, EquipmentQuery equipment) {
        this.id = id;
        this.name = name;
        this.scheduledMaintenance = scheduledMaintenanceDate;
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getScheduledMaintenance() {
        return scheduledMaintenance;
    }

    public EquipmentQuery getEquipment() {
        return equipment;
    }
}
