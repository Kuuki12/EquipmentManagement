package pl.inzynierka.Equipment.Query;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class EquipmentQuery {
    private String name;
    private String image;
    private String emailOwner;
    private Integer priority;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastMaintenanceDate;
    private Integer maintenanceFrequency;
    private String description;
    private String serialNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfMinimumDurability;

    public EquipmentQuery(String name, String image, String emailOwner, Integer priority, LocalDate lastMaintenanceDate, Integer maintenanceFrequency, String description, String serialNumber, LocalDate productionDate) {
        this.name = name;
        this.image = image;
        this.emailOwner = emailOwner;
        this.priority = priority;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.maintenanceFrequency = maintenanceFrequency;
        this.description = description;
        this.serialNumber = serialNumber;
        this.productionDate = productionDate;
        this.dateOfMinimumDurability = lastMaintenanceDate.plusDays(maintenanceFrequency);
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getEmailOwner() {
        return emailOwner;
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

    public String getDescription() {
        return description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public LocalDate getDateOfMinimumDurability() {
        return dateOfMinimumDurability;
    }
}
