package pl.inzynierka.Maintenance.Query;

import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.Equipment.Query.EquipmentQuery;

import java.time.LocalDate;

public class UpdateMaintenanceQuery {
    private Long id;
    private String name;
    private Long idEquipment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledMaintenanceDate;

    public UpdateMaintenanceQuery(Long id, String name, LocalDate scheduledMaintenanceDate, Long idEquipment) {
        this.id = id;
        this.name = name;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
        this.idEquipment = idEquipment;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getScheduledMaintenanceDate() {
        return scheduledMaintenanceDate;
    }

    public Long getIdEquipment() {
        return idEquipment;
    }
}
