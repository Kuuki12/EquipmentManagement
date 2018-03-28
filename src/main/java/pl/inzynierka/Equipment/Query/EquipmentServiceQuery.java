package pl.inzynierka.Equipment.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.EquipmentDoesntExtist;
import pl.inzynierka.Pagination;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentServiceQuery {

    EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentServiceQuery(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Page<EquipmentsQuery> getAllEquipments(Pageable pageable) {
        return equipmentRepository.findAllEquipments(pageable);
    }

    public Page<EquipmentsQuery> getClientEquipments(String username, Pageable pageable){

        return equipmentRepository.findAllEquipmentsUser(username, pageable);
    }

    public EquipmentQuery getEquipment(Long IDEquipment){

        Equipment equipment = equipmentRepository.findById(IDEquipment);

        if(equipment == null) throw new EquipmentDoesntExtist();

        return new EquipmentQuery(equipment.getName(), equipment.getImage().getName(), equipment.getClient().getUser().getUsername(), equipment.getPriority(), equipment.getLastMaintenanceDate(), equipment.getMaintenanceFrequency(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getProductionDate());
    }

}
