package pl.inzynierka.Maintenance.Command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Exceptions.UserDoesntExist;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.Repairman.Domain.Employee;
import pl.inzynierka.Repairman.Repository.RepairmanRepository;
import pl.inzynierka.User.Repository.UserRepository;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.User.Domain.User;

@Service
public class MaintenanceService {

	MaintenanceRepository maintenanceRepository;
    EquipmentRepository equipmentDAO;
    UserRepository userDAO;
    RepairmanRepository repairmanRepository;

    @Autowired
	public MaintenanceService(MaintenanceRepository maintenanceRepository, EquipmentRepository equipmentDAO, UserRepository userDAO, RepairmanRepository repairmanRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.equipmentDAO = equipmentDAO;
		this.userDAO = userDAO;
		this.repairmanRepository = repairmanRepository;
	}

	public void create(Maintenance maintenance){
		maintenanceRepository.save(maintenance);
	}

	public void delete(Long id){

    	Maintenance maintenance = maintenanceRepository.findOne(id);

		if(maintenance == null)throw new MaintenanceDoesntExist();

    	Equipment equipment = maintenance.getEquipment();
		equipment.removeMaintenance(maintenance);

		equipmentDAO.save(equipment);
		maintenanceRepository.delete(id);
	}
	
	public void take(Long id, String username){
		
		Maintenance maintenance = maintenanceRepository.findById(id);
		if(maintenance == null) throw new MaintenanceDoesntExist();

		Employee repairman = repairmanRepository.findEmployeeByUsername(username);
		if(repairman == null) throw new UserDoesntExist();

		repairman.assignMaintenance(maintenance);
		maintenance.takeMaintenance(repairman);
		repairmanRepository.save(repairman);
	}
	
	public List<Maintenance> listAllRepairmanMaintenancesWithProgress(String username, String progress){
		
		User repairman = userDAO.findByUsername(username);

		if(repairman == null) throw new UserDoesntExist();
		List<Maintenance> maintenances = maintenances = maintenanceRepository.findByRepairmanAndProgress(repairman, progress);
		
		return maintenances;
	}
	
}
