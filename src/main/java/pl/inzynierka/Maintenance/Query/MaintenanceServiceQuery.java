package pl.inzynierka.Maintenance.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.inzynierka.Equipment.Query.EquipmentQuery;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Priority.PriorityStatus;
import pl.inzynierka.Progress.ProgressStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceQuery {

    MaintenanceRepository maintenanceRepository;

    @Autowired
    public MaintenanceServiceQuery(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    public MaintenanceQuery getMaintenance(Long id){
        Maintenance maintenance = maintenanceRepository.findById(id);

        if(maintenance == null) throw new MaintenanceDoesntExist();

        EquipmentQuery equipmentQuery = new EquipmentQuery(maintenance.getEquipment().getName(), maintenance.getEquipment().getImage().getName(), maintenance.getEquipment().getClient().getUser().getUsername(), maintenance.getEquipment().getPriority(), maintenance.getEquipment().getLastMaintenanceDate(), maintenance.getEquipment().getMaintenanceFrequency(), maintenance.getEquipment().getDescription(),maintenance.getEquipment().getSerialNumber(), maintenance.getEquipment().getProductionDate());

        MaintenanceQuery maintenanceQuery = new MaintenanceQuery(
                maintenance.getId(),
                maintenance.getName(),
                maintenance.getDateScheduled(),
                equipmentQuery);

        return maintenanceQuery;
    }

    public UpdateMaintenanceQuery getUpdateMaintenance(Long id){
        Maintenance maintenance = maintenanceRepository.findById(id);
        if(maintenance == null) throw new MaintenanceDoesntExist();

        return new UpdateMaintenanceQuery(maintenance.getId(), maintenance.getName(), maintenance.getDateScheduled(), maintenance.getEquipment().getIdEquipment());
    }

    public Page<MaintenancesQuery> listAllMaintenancesWithProgressAndPriority(ProgressStatus progress, PriorityStatus priority, Pageable pageable){
        return maintenanceRepository.findAllWithProgressAndPriority(progress, priority, pageable);
    }

    public Page<MaintenancesQuery> listAllMaintenancesWithProgress(ProgressStatus progress, Pageable pageable){
        return maintenanceRepository.findAllWithProgress(progress, pageable);
    }

    public Page<MaintenancesQuery> listEmployeeMaintenancesWithProgress(String username, ProgressStatus progress, Pageable pageable){
        return maintenanceRepository.findEmployeeMaintenancesWithProgress(progress, username, pageable);
    }

}
