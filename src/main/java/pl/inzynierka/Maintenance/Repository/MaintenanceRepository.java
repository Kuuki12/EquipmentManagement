package pl.inzynierka.Maintenance.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Query.MaintenancesQuery;
import pl.inzynierka.Priority.PriorityStatus;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.User.Domain.User;

import java.lang.String;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

public interface MaintenanceRepository extends CrudRepository<Maintenance, Long>{

	Maintenance findById(Long serialCode);

	List<Maintenance> findByRepairmanAndProgress(User repairman, String progress);
	
	@Query("SELECT m FROM Maintenance m WHERE (m.progress = ?1 AND m.dateScheduled BETWEEN ?2 AND ?3) OR (m.progress = ?1 AND m.dateScheduled < ?2 )")
	List<Maintenance> findAllMaintenanceWith30DaysLeft(ProgressStatus progressStatus, LocalDate startDate, LocalDate endDate);

	@Query("SELECT new pl.inzynierka.Maintenance.Query.MaintenancesQuery(m.id, m.name, m.dateScheduled, m.progress) FROM Maintenance m WHERE m.progress = ?1 AND m.priority = ?2")
	Page<MaintenancesQuery> findAllWithProgressAndPriority(ProgressStatus progress, PriorityStatus priority, Pageable pageable);

	@Query("SELECT new pl.inzynierka.Maintenance.Query.MaintenancesQuery(m.id, m.name, m.repairman.user, m.dateScheduled, m.dateTaken, m.dateDone, m.progress) FROM Maintenance m WHERE m.progress = ?1")
	Page<MaintenancesQuery> findAllWithProgress(ProgressStatus progress, Pageable pageable);

	@Query("SELECT new pl.inzynierka.Maintenance.Query.MaintenancesQuery(m.id, m.name, m.repairman.user, m.dateScheduled, m.dateTaken, m.dateDone, m.progress) FROM Maintenance m WHERE m.progress = ?1 AND m.repairman.user.username = ?2")
	Page<MaintenancesQuery> findEmployeeMaintenancesWithProgress(ProgressStatus progress, String username, Pageable pageable);

}
