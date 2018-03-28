package pl.inzynierka.Report.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import java.lang.String;

public interface ReportOfMaintenanceRepository extends CrudRepository<ReportOfMaintenance, Long>{

}
