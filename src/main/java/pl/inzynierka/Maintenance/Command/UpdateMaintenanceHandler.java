package pl.inzynierka.Maintenance.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.EquipmentDoesntExtist;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;

@Component
public class UpdateMaintenanceHandler {

    @Autowired
    MaintenanceService maintenanceService;

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @CommandHandler
    public void handle(UpdateMaintenanceCommand updateMaintenanceCommand){

        Maintenance maintenance = maintenanceRepository.findById(updateMaintenanceCommand.getId());
        if(maintenance == null) throw new MaintenanceDoesntExist();

        Equipment equipment = equipmentRepository.findById(updateMaintenanceCommand.getIdEquipment());
        if(equipment == null) throw new EquipmentDoesntExtist();

        maintenance.changeInformation(updateMaintenanceCommand.getName(),
                updateMaintenanceCommand.getScheduledMaintenanceDate(),
                equipment);

        maintenanceService.create(maintenance);
    }
}
