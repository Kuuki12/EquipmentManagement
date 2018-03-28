package pl.inzynierka.Equipment.Command;

import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Client.Repository.ClientRepository;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Exceptions.UserDoesntExist;
import pl.inzynierka.Image.Command.ImageServiceCommand;
import pl.inzynierka.Image.Domain.Image;

import java.io.IOException;

@Component
public class AddEquipmentHandler {

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ImageServiceCommand imageServiceCommand;

    @CommandHandler
    public void handle(AddEquipmentCommand addEquipmentCommand) throws IOException {

        Client client = validWhetherOwnerEquipmentExists(addEquipmentCommand.getEmailOwner());

        Image image = new Image(addEquipmentCommand.getImage().getBytes());

        imageServiceCommand.create(image);

        Equipment equipment = new Equipment(
                addEquipmentCommand.getName(),
                image,
                client,
                addEquipmentCommand.getPriority(),
                addEquipmentCommand.getLastMaintenanceDate(),
                addEquipmentCommand.getMaintenanceFrequency(),
                addEquipmentCommand.getDescription(),
                addEquipmentCommand.getSerialNumber(),
                addEquipmentCommand.getProductionDate()
        );

        equipmentService.save(equipment);
    }

    private Client validWhetherOwnerEquipmentExists(String email){

        Client client = clientRepository.findByUserName(email);

        if(client == null){
            throw new UserDoesntExist();
        }

        return client;
    }
}
