package pl.inzynierka.Equipment.Command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import pl.inzynierka.validator.ContentTypeConstraint;
import pl.inzynierka.validator.NotFileConstraint;
import pl.inzynierka.validator.UserNotExistsConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class AddEquipmentCommand {
    @NotEmpty private String name;
    @NotFileConstraint @ContentTypeConstraint(value = {"image/png", "image/jpeg"}, message = "Dozwolone tylko rozszerzenia .png i .jpg") private MultipartFile image;
    @NotEmpty
//    @UserNotExistsConstraint
    private String emailOwner;
    @NotNull @Min(0) @Max(100) private Integer priority;
    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate lastMaintenanceDate;
    @NotNull @Min(1) @Max(1000) private Integer maintenanceFrequency;
    @NotEmpty @Size(max = 4000) private String description;
    @NotEmpty private String serialNumber;
    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate productionDate;

    public AddEquipmentCommand() {
    }

    public AddEquipmentCommand(String title, MultipartFile image, String emailOwner, Integer priority, LocalDate lastMaintenanceDate, Integer maintenanceFrequency, String description, String serialNumber, LocalDate productionDate) {
        this.name = title;
        this.image = image;
        this.emailOwner = emailOwner;
        this.priority = priority;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.maintenanceFrequency = maintenanceFrequency;
        this.description = description;
        this.serialNumber = serialNumber;
        this.productionDate = productionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Integer getMaintenanceFrequency() {
        return maintenanceFrequency;
    }

    public void setMaintenanceFrequency(Integer maintenanceFrequency) {
        this.maintenanceFrequency = maintenanceFrequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }
}
