package pl.inzynierka.Report.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;

import java.io.IOException;

@Component
public class UpdateReportHandler {

    @Autowired
    ReportOfMaintenanceService reportOfMaintenanceService;

    @Autowired
    ReportOfMaintenanceRepository reportOfMaintenanceRepository;

    @CommandHandler
    public void handle(UpdateReportCommand updateReportCommand) throws IOException{
        ReportOfMaintenance report = reportOfMaintenanceService.getReportOfMaintenance(updateReportCommand.getIdMaintenance());

        if(updateReportCommand.getFile().getSize() == 0L || updateReportCommand.getFile().getOriginalFilename().isEmpty() == true){
            report.update(updateReportCommand.getName(), updateReportCommand.getDescription(), null);
        }else{
            report.update(updateReportCommand.getName(), updateReportCommand.getDescription(), updateReportCommand.getFile().getBytes());
        }

        reportOfMaintenanceRepository.save(report);
    }
}
