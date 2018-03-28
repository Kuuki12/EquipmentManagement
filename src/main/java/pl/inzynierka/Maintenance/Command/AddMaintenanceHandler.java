package pl.inzynierka.Maintenance.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.EquipmentDoesntExtist;
import pl.inzynierka.Maintenance.Domain.Maintenance;

@Component
public class AddMaintenanceHandler {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    MaintenanceService maintenanceService;

    @CommandHandler
    public void handle(AddMaintenanceCommand addMaintenanceCommand){

        Equipment equipment = equipmentRepository.findById(addMaintenanceCommand.getIdEquipment());
        if(equipment == null) throw new EquipmentDoesntExtist();

        Maintenance maintenance = new Maintenance(
                addMaintenanceCommand.getName(),
                addMaintenanceCommand.getScheduledMaintenanceDate(),
                equipment);

        maintenanceService.create(maintenance);
    }
}
