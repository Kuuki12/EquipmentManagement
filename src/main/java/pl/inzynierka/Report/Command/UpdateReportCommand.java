package pl.inzynierka.Report.Command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class UpdateReportCommand {

    private Long idMaintenance;
    private Long idReport;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private MultipartFile file;

    public UpdateReportCommand() {
    }

    public UpdateReportCommand(String name, String description, MultipartFile file) {
        this.name = name;
        this.description = description;
        this.file = file;
    }

    public Long getIdMaintenance() {
        return idMaintenance;
    }

    public void setIdMaintenance(Long idMaintenance) {
        this.idMaintenance = idMaintenance;
    }

    public Long getIdReport() {
        return idReport;
    }

    public void setIdReport(Long idReport) {
        this.idReport = idReport;
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
