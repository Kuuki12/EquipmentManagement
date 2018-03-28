package pl.inzynierka.Equipment.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Query.EquipmentsQuery;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Progress.ProgressStatus;

import java.lang.Long;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import java.lang.String;

public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Long>{

	@Query("SELECT e FROM Equipment e WHERE e.id = ?1")
	Equipment findById(Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Equipment e SET e.name = ?1, e.client.user = ?2 WHERE e.idEquipment = ?3")
	int update(String name, Client client, Long idEquipment);

	@Query("SELECT new pl.inzynierka.Equipment.Query.EquipmentsQuery(e.idEquipment, e.name, e.client.user.username) FROM Equipment e")
	Page<EquipmentsQuery> findAllEquipments(Pageable pageable);

	@Query("SELECT new pl.inzynierka.Equipment.Query.EquipmentsQuery(e.idEquipment, e.name, e.client.user.username) FROM Equipment e WHERE e.client.user.username = ?1")
	Page<EquipmentsQuery> findAllEquipmentsUser(String username, Pageable pageable);

	List<Equipment> findAll();

}
