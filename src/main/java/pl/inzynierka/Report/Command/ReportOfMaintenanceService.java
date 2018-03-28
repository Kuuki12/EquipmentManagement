package pl.inzynierka.Report.Command;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.inzynierka.Client.Repository.ClientRepository;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.ClientDoesntExist;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Exceptions.ReportOfMaintenanceDoesntExist;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;

@Service
public class ReportOfMaintenanceService {

	@Autowired
    ReportOfMaintenanceRepository reportOfMaintenanceDAO;
	
	@Autowired
	MaintenanceRepository maintenanceDAO;
	
	@Autowired
    EquipmentRepository equipmentDAO;
	
	public void save(Long idMaintenance, ReportOfMaintenance report){
		
		Maintenance maintenance = maintenanceDAO.findById(idMaintenance);
		Equipment equipment = maintenance.getEquipment();

		maintenance.doneMaintenance(report);
		equipment.updateLastMaintenanceDate();

		equipmentDAO.save(equipment);
		reportOfMaintenanceDAO.save(report);
		maintenanceDAO.save(maintenance);
	}

	public void update(Long serialCodeOfReport, ReportOfMaintenance reportOfMaintenance){

		ReportOfMaintenance report = reportOfMaintenanceDAO.findOne(serialCodeOfReport);
		report.update(reportOfMaintenance.getName(), reportOfMaintenance.getDescription(), reportOfMaintenance.getFile());

		reportOfMaintenanceDAO.save(report);
	}

	public void delete(Long id){
		reportOfMaintenanceDAO.delete(id);
	}

	public ReportOfMaintenance getReportOfMaintenance(Long idMaintenance){
		
		Maintenance maintenance = maintenanceDAO.findById(idMaintenance);
		
		if(maintenance == null) throw new MaintenanceDoesntExist();
		if(maintenance.getReport() == null) throw new ReportOfMaintenanceDoesntExist();

		return maintenance.getReport();
	}

}
