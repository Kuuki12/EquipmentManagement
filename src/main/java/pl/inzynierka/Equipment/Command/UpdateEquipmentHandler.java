package pl.inzynierka.Equipment.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Client.Repository.ClientRepository;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.EquipmentDoesntExtist;
import pl.inzynierka.Exceptions.UserDoesntExist;
import pl.inzynierka.Image.Domain.Image;
import pl.inzynierka.Image.Repository.ImageRepository;

import java.io.IOException;

@Component
public class UpdateEquipmentHandler {

    @Autowired
    EquipmentRepository equipmentDAO;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    ImageRepository imageRepository;

    @CommandHandler
    public void handle(UpdateEquipmentCommand updateEquipmentCommand) throws IOException {

        Equipment equipment = equipmentDAO.findById(updateEquipmentCommand.getId());
        if(equipment == null) throw new EquipmentDoesntExtist();

        Client client = clientRepository.findByUserName(updateEquipmentCommand.getEmailOwner());
        if(client == null) throw new UserDoesntExist();

        if(updateEquipmentCommand.getImage().getSize() == 0L || updateEquipmentCommand.getImage().getOriginalFilename().isEmpty() == true){
            equipment.changeInformation(updateEquipmentCommand.getName(), null, client, updateEquipmentCommand.getPriority(), updateEquipmentCommand.getLastMaintenanceDate(), updateEquipmentCommand.getMaintenanceFrequency(), updateEquipmentCommand.getDescription(), updateEquipmentCommand.getSerialNumber(), updateEquipmentCommand.getProductionDate());
        }else{
            Image image = new Image(updateEquipmentCommand.getImage().getBytes());
            imageRepository.save(image);
            equipment.changeInformation(updateEquipmentCommand.getName(), image, client, updateEquipmentCommand.getPriority(), updateEquipmentCommand.getLastMaintenanceDate(), updateEquipmentCommand.getMaintenanceFrequency(), updateEquipmentCommand.getDescription(), updateEquipmentCommand.getSerialNumber(), updateEquipmentCommand.getProductionDate());
        }

        equipmentService.update(equipment);
    }
}
