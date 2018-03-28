package pl.inzynierka.Repairman.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.inzynierka.Maintenance.Query.MaintenancesQuery;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.Repairman.Domain.Employee;

public interface RepairmanRepository extends CrudRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.user.username = ?1")
    Employee findEmployeeByUsername(String username);

}
