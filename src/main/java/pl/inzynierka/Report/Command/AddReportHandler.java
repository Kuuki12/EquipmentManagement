package pl.inzynierka.Report.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;

import java.io.IOException;

@Component
public class AddReportHandler {

    @Autowired
    ReportOfMaintenanceService reportOfMaintenanceService;

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @CommandHandler
    public void handle(AddReportCommand addReportCommand) throws IOException{
        if(maintenanceRepository.findOne(addReportCommand.getidMaintenance()) == null) throw new MaintenanceDoesntExist();

        ReportOfMaintenance report = new ReportOfMaintenance(addReportCommand.getName(), addReportCommand.getDescription(), addReportCommand.getFile().getBytes());
        reportOfMaintenanceService.save(addReportCommand.getidMaintenance(), report);
    }
}
