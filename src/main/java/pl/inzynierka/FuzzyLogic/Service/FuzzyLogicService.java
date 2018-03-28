package pl.inzynierka.FuzzyLogic.Service;

import com.fuzzylite.Engine;
import com.fuzzylite.activation.General;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.imex.FllImporter;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Trapezoid;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.FuzzyLogic.TriggeredRule;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Priority.PriorityStatus;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FuzzyLogicService {

    Engine engine;
    InputVariable equipment;
    InputVariable maintenance;
    OutputVariable priority;
    RuleBlock rule_block;

    MaintenanceRepository maintenanceRepository;
    EquipmentRepository equipmentRepository;

    @Autowired
    public FuzzyLogicService(MaintenanceRepository maintenanceRepository, EquipmentRepository equipmentRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentRepository = equipmentRepository;

        try{
            configFuzzyLogic();
        }catch (IOException e){
            System.out.println("blad");
        }
    }

    public void refreshFuzzyLogic(){
        LocalDate dateToday = LocalDate.now();
        LocalDate datePlus30Days = LocalDate.now().plusDays(30L);

        List<Maintenance> maintenances = maintenanceRepository.findAllMaintenanceWith30DaysLeft(ProgressStatus.WAITING, dateToday, datePlus30Days);
        maintenances.addAll(findAllMaintenancesToRefresh());

        for (Maintenance maintenance1: maintenances) {
            org.joda.time.LocalDate dateScheduled = new org.joda.time.LocalDate(maintenance1.getDateScheduled().getYear(), maintenance1.getDateScheduled().getMonthValue(), maintenance1.getDateScheduled().getDayOfMonth());
            Integer days = Days.daysBetween(org.joda.time.LocalDate.now(), dateScheduled).getDays();
            Integer daysToInteger = (days < 0) ? 0 : days;

            Double daysToMaintenance = Double.parseDouble(daysToInteger.toString());
            Double equipmentPriority = Double.parseDouble(maintenance1.getEquipment().getPriority().toString());
            maintenance.setValue(daysToMaintenance);
            equipment.setValue(equipmentPriority);
            engine.process();
            maintenance1.changePriority(PriorityStatus.valueOf(priority.highestMembershipTerm(priority.getValue()).getName()));
        }
        maintenanceRepository.save(maintenances);
    }

    private List<Maintenance> findAllMaintenancesToRefresh(){

        List<Maintenance> maintenancesToRefresh = new ArrayList<>();
        List<Equipment> equipments = equipmentRepository.findAll();

        for (Equipment e:equipments) {
            if(e.getMaintenances().stream().allMatch(p -> p.getProgress().equals(ProgressStatus.DONE))){

                org.joda.time.LocalDate dateOfMinimumDurability = new org.joda.time.LocalDate(e.getDateOfMinimumDurability().getYear(), e.getDateOfMinimumDurability().getMonthValue(), e.getDateOfMinimumDurability().getDayOfMonth());
                Integer days = Days.daysBetween(org.joda.time.LocalDate.now(), dateOfMinimumDurability).getDays();

                if(days <= 30){
                    maintenancesToRefresh.add(new Maintenance(e.getName(), e.getDateOfMinimumDurability(), e));
                }

            }
        }

        return maintenancesToRefresh;
    }

    public List<TriggeredRule> getTriggeredRules(Maintenance maintenance){
        Double equipmentPriority = Double.parseDouble(maintenance.getEquipment().getPriority().toString());

        org.joda.time.LocalDate dateScheduled = new org.joda.time.LocalDate(maintenance.getDateScheduled().getYear(), maintenance.getDateScheduled().getMonthValue(), maintenance.getDateScheduled().getDayOfMonth());
        Integer days = Days.daysBetween(org.joda.time.LocalDate.now(), dateScheduled).getDays();
        if(days > 30) return null;

        Integer daysToInteger = (days < 0) ? 0 : days;
        Double daysToMaintenance = Double.parseDouble(daysToInteger.toString());

        this.maintenance.setValue(daysToMaintenance);
        this.equipment.setValue(equipmentPriority);

        engine.process();

        int i=0;
        List<TriggeredRule> triggeredRules = new ArrayList<>();
        for (Rule rule:rule_block.getRules()) {
            if(rule.isTriggered()) triggeredRules.add(new TriggeredRule(i, rule.getText()));
            i++;
        }
        return triggeredRules;
    }

    private void configFuzzyLogic()throws IOException{

        Resource resource = new ClassPathResource("/static/fuzzylogic/ApplicationFuzzyLogic.fll");

        engine = new FllImporter().fromFile(resource.getFile());

        StringBuilder status = new StringBuilder();
        if (! engine.isReady(status)) throw new RuntimeException("[engine error] engine is not ready:n" + status);
        engine.process();

        equipment = engine.getInputVariable("EQUIPMENT");
        maintenance = engine.getInputVariable("MAINTENANCE");
        priority = engine.getOutputVariable("PRIORITY");
        rule_block = engine.getRuleBlock("RULE_BLOCK");
    }

}
