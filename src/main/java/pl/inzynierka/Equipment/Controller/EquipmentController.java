package pl.inzynierka.Equipment.Controller;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pl.inzynierka.Equipment.Command.AddEquipmentCommand;
import pl.inzynierka.Equipment.Command.UpdateEquipmentCommand;
import pl.inzynierka.Equipment.Query.EquipmentQuery;
import pl.inzynierka.Equipment.Query.EquipmentServiceQuery;
import pl.inzynierka.Equipment.Query.EquipmentsQuery;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Command.EquipmentService;
import pl.inzynierka.Exceptions.EquipmentDoesntExtist;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Pagination;

import java.util.List;

@Controller
public class EquipmentController {

    EquipmentService equipmentService;
    EquipmentServiceQuery equipmentServiceQuery;
    CommandGateway commandGateway;
    EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentController(EquipmentService equipmentService, EquipmentServiceQuery equipmentServiceQuery, CommandGateway commandGateway, EquipmentRepository equipmentRepository) {
        this.equipmentService = equipmentService;
        this.equipmentServiceQuery = equipmentServiceQuery;
        this.commandGateway = commandGateway;
        this.equipmentRepository = equipmentRepository;
    }

    @RequestMapping(value = "/equipments/create", method = RequestMethod.GET)
    public ModelAndView showCreateForm() {

        ModelAndView mav = new ModelAndView("/equipment/create");

        mav.addObject("equipment", new AddEquipmentCommand());

        return mav;
    }

    @RequestMapping(value = "/equipments/create", method = RequestMethod.POST)
    public String createEquipment(@Valid @ModelAttribute("equipment") AddEquipmentCommand addEquipmentCommand, BindingResult result) {

        if (result.hasErrors()) return "/equipment/create";
        commandGateway.send(addEquipmentCommand);
        return "redirect:/equipments";
    }

    @RequestMapping(value = "/equipments/{serialCode}/update", method = RequestMethod.GET)
    public ModelAndView showUpdateForm(@PathVariable("serialCode") Long id) {
        ModelAndView mav = new ModelAndView("/equipment/update");

        mav.addObject("serialCode", id);
        mav.addObject("equipment", equipmentServiceQuery.getEquipment(id));

        return mav;
    }

    @RequestMapping(value = "/equipments/{serialCode}/update", method = RequestMethod.POST)
    public String updateEquipment(@PathVariable("serialCode") Long id, @Valid @ModelAttribute("equipment") UpdateEquipmentCommand updateEquipmentCommand, BindingResult result) {

        if (result.hasErrors()) return "/equipment/update";
        updateEquipmentCommand.setId(id);
        commandGateway.send(updateEquipmentCommand);
        return "redirect:/equipments";
    }

    @RequestMapping(value = "/equipments/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteEquipment(@PathVariable("id") Long id) {
        equipmentService.delete(id);
    }

    @RequestMapping(value = "/equipments/{id}", method = RequestMethod.GET)
    public ModelAndView getEquipment(@PathVariable("id") Long id) {

        ModelAndView mav = new ModelAndView("/equipment/selected");

        List<Maintenance> maintenances = (equipmentRepository.findById(id) != null) ? equipmentRepository.findById(id).getMaintenances() : null;

        mav.addObject("equipment", equipmentServiceQuery.getEquipment(id));
        mav.addObject("maintenances", maintenances);

        return mav;
    }

    @RequestMapping(value = "/equipments", method = RequestMethod.GET)
    public ModelAndView showAllEquipments(@RequestParam(required = false, defaultValue = "0") Integer page,
                                          @RequestParam(required = false, defaultValue = "20") Integer size) {

        Page<EquipmentsQuery> equipmentsQueries = null;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasClientRole = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));
        equipmentsQueries = (hasClientRole) ? equipmentServiceQuery.getClientEquipments(username, new PageRequest(page,size, Sort.Direction.DESC, "idEquipment")) : equipmentServiceQuery.getAllEquipments(new PageRequest(page,size, Sort.Direction.DESC, "idEquipment"));

        Pagination pagination = (equipmentsQueries != null) ? new Pagination(equipmentsQueries, "equipments") : null;

        ModelAndView mav = new ModelAndView("/equipment/list");

        mav.addObject("equipments", equipmentsQueries);
        mav.addObject("page", pagination);

        return mav;
    }

}


