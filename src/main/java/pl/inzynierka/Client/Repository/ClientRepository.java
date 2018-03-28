package pl.inzynierka.Client.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long>{

	@Query("SELECT c FROM Client c WHERE c.user.username = ?1")
	Client findByUserName(String userName);
	
	@Query("SELECT r FROM Client c JOIN c.equipments e JOIN e.maintenances m JOIN m.report r WHERE c.user.username = ?1")
	List<ReportOfMaintenance> findAllReportsOfMaintenanceClient(String userName);
	
	@Query("SELECT r FROM Client c JOIN c.equipments e JOIN e.maintenances m JOIN m.report r WHERE c.user.username = ?1 AND e.idEquipment = ?2")
	List<ReportOfMaintenance> findAllReportsOfMaintenanceClientEquipment(String userName, Long idEquipment);
	
	@Query("SELECT r FROM Client c JOIN c.equipments e JOIN e.maintenances m JOIN m.report r WHERE r.dateCreation ="
			+ "(SELECT MAX(r.dateCreation) FROM Client c JOIN c.equipments e JOIN e.maintenances m JOIN m.report r WHERE c.user.username = ?1 AND e.idEquipment = ?2 )")
	ReportOfMaintenance findTheLatestReportOfMaintenanceEquipment(String userName, Long idEquipment);
	
}
