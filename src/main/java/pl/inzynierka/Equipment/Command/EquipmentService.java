package pl.inzynierka.Equipment.Command;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.inzynierka.Client.Repository.ClientRepository;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.EquipmentDoesntExtist;
import pl.inzynierka.Image.Repository.ImageRepository;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.User.Repository.UserRepository;
import pl.inzynierka.Equipment.Domain.Equipment;

@Service
public class EquipmentService {

	EquipmentRepository equipmentDAO;
	ImageRepository imageDAO;
    UserRepository userDAO;
    ClientRepository clientDAO;

    @Autowired
	MaintenanceRepository maintenanceRepository;

    @Autowired
	public EquipmentService(EquipmentRepository equipmentDAO, ImageRepository imageDAO, UserRepository userDAO, ClientRepository clientDAO) {
		this.equipmentDAO = equipmentDAO;
		this.imageDAO = imageDAO;
		this.userDAO = userDAO;
		this.clientDAO = clientDAO;
	}

	public void save(Equipment equipment){

		LocalDate.now().isAfter(LocalDate.now());

		LocalDate dateMaintenanceScheduled = equipment.getLastMaintenanceDate().plusDays(equipment.getMaintenanceFrequency());

		Maintenance maintenance = new Maintenance(equipment.getName(), dateMaintenanceScheduled, equipment);

		equipmentDAO.save(equipment);
		maintenanceRepository.save(maintenance);
	}

	public void update(Equipment equipment){
		equipmentDAO.save(equipment);
	}

	public void delete(Long id){

		Equipment equipment = equipmentDAO.findById(id);

		if(equipment == null) throw new EquipmentDoesntExtist();

		equipmentDAO.delete(equipment);
	}
}
