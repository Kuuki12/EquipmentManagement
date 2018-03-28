package pl.inzynierka.Report.Command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import pl.inzynierka.validator.ContentTypeConstraint;
import pl.inzynierka.validator.NotFileConstraint;

public class AddReportCommand {
    private Long idMaintenance;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotFileConstraint @ContentTypeConstraint(value = {"application/pdf"}, message = "Dozwolone tylko rozszerzenia .pdf") private MultipartFile file;

    public AddReportCommand() {
    }

    public AddReportCommand(String name, String description, MultipartFile file) {
        this.name = name;
        this.description = description;
        this.file = file;
    }

    public Long getidMaintenance() {
        return idMaintenance;
    }

    public void setidMaintenance(Long idMaintenance) {
        this.idMaintenance = idMaintenance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
