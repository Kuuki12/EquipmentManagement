package pl.inzynierka.Maintenance.Command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.validator.EquipmentDoesntExistConstraint;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class UpdateMaintenanceCommand {

    private Long id;
    @NotEmpty
    private String name;
    @NotNull
//    @EquipmentDoesntExistConstraint
    private Long idEquipment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledMaintenanceDate;

    public UpdateMaintenanceCommand() {
    }

    public UpdateMaintenanceCommand(String name, Long idEquipment, LocalDate scheduledMaintenanceDate) {
        this.name = name;
        this.idEquipment = idEquipment;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(Long idEquipment) {
        this.idEquipment = idEquipment;
    }

    public LocalDate getScheduledMaintenanceDate() {
        return scheduledMaintenanceDate;
    }

    public void setScheduledMaintenanceDate(LocalDate scheduledMaintenanceDate) {
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
    }

}
