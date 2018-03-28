package pl.inzynierka.Maintenance.Command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.inzynierka.validator.EquipmentDoesntExistConstraint;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class AddMaintenanceCommand {

    @NotEmpty
    private String name;
    @NotNull
//    @EquipmentDoesntExistConstraint
    private Long idEquipment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduledMaintenanceDate;

    public AddMaintenanceCommand() {
    }

    public AddMaintenanceCommand(String name, Long idEquipment, LocalDate scheduledMaintenanceDate) {
        this.name = name;
        this.idEquipment = idEquipment;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
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
