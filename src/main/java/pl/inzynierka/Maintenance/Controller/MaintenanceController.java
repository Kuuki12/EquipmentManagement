package pl.inzynierka.Maintenance.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pl.inzynierka.Equipment.Command.AddEquipmentCommand;
import pl.inzynierka.FuzzyLogic.Service.FuzzyLogicService;
import pl.inzynierka.FuzzyLogic.TriggeredRule;
import pl.inzynierka.Maintenance.Command.AddMaintenanceCommand;
import pl.inzynierka.Maintenance.Command.UpdateMaintenanceCommand;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Query.*;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Pagination;
import pl.inzynierka.Priority.PriorityStatus;
import pl.inzynierka.Progress.ProgressStatus;
import pl.inzynierka.Repairman.Domain.Employee;
import pl.inzynierka.Repairman.Repository.RepairmanRepository;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import pl.inzynierka.Maintenance.Command.MaintenanceService;
import pl.inzynierka.Report.Command.ReportOfMaintenanceService;

@Controller
public class MaintenanceController {

	MaintenanceService maintenanceService;
	ReportOfMaintenanceService reportOfMaintenanceService;
	MaintenanceServiceQuery maintenanceServiceQuery;
	CommandGateway commandGateway;
	FuzzyLogicService fuzzyLogicService;
	MaintenanceRepository maintenanceRepository;
	RepairmanRepository repairmanRepository;

	@Autowired
	public MaintenanceController(MaintenanceService maintenanceService,
								 ReportOfMaintenanceService reportOfMaintenanceService,
								 MaintenanceServiceQuery maintenanceServiceQuery,
								 CommandGateway commandGateway,
								 FuzzyLogicService fuzzyLogicService,
								 MaintenanceRepository maintenanceRepository,
								 RepairmanRepository repairmanRepository) {

		this.maintenanceService = maintenanceService;
		this.reportOfMaintenanceService = reportOfMaintenanceService;
		this.maintenanceServiceQuery = maintenanceServiceQuery;
		this.commandGateway = commandGateway;
		this.fuzzyLogicService = fuzzyLogicService;
		this.maintenanceRepository = maintenanceRepository;
		this.repairmanRepository = repairmanRepository;
	}

	@RequestMapping(value = "/maintenances/create", method = RequestMethod.GET)//GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showCreateMaintenanceForm(){
		
		ModelAndView mav = new ModelAndView("/maintenance/create");
		
		mav.addObject("maintenance", new AddMaintenanceCommand());

		return mav;
	}
	
	@RequestMapping(value = "/maintenances/create", method = RequestMethod.POST)//GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public String createMaintenance(@Valid @ModelAttribute("maintenance") AddMaintenanceCommand maintenance, BindingResult result){
		
		if(result.hasErrors()){
			return "/maintenance/create";
		}

		commandGateway.send(maintenance);
		return "redirect:/maintenances/waiting";
	}
	
	@RequestMapping(value = "/maintenances/{id}/update", method = RequestMethod.GET)// GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showUpdateMaintenanceForm(@PathVariable("id") Long id){
		
		ModelAndView mav = new ModelAndView("/maintenance/update");
		UpdateMaintenanceQuery updateMaintenanceQuery = maintenanceServiceQuery.getUpdateMaintenance(id);
		mav.addObject("maintenance", updateMaintenanceQuery);

		return mav;
	}
	
	@RequestMapping(value = "/maintenances/{id}/update", method = RequestMethod.POST)// GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public String updateMaintenance(@PathVariable("id") Long id, @Valid @ModelAttribute("maintenance") UpdateMaintenanceCommand maintenance, BindingResult result){

		if(result.hasErrors()){
			return "/maintenance/update";
		}

		maintenance.setId(id);
		commandGateway.send(maintenance);

		return "redirect:/maintenances/waiting";
	}
	
	@RequestMapping(value = "/maintenances/{id}", method = RequestMethod.DELETE)//GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public @ResponseBody void deleteMaintenance(@PathVariable("id") Long id){
		
		maintenanceService.delete(id);
	}
	
	@RequestMapping(value = "/maintenances/{id}/waiting", method = RequestMethod.GET)// GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showSelectedMaintenanceWaitingForm(@PathVariable("id") Long id){
		
		ModelAndView mav = new ModelAndView("/maintenance/selected/waiting");
		
		MaintenanceQuery maintenance = maintenanceServiceQuery.getMaintenance(id);

		List<TriggeredRule> rules = fuzzyLogicService.getTriggeredRules(maintenanceRepository.findOne(id));

		mav.addObject("rules", rules);
		mav.addObject("maintenance", maintenance);

		return mav;
	}

	@RequestMapping(value = "/maintenances/{id}/doing", method = RequestMethod.GET)// GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showSelectedMaintenanceDoingForm(@PathVariable("id") Long id){

		ModelAndView mav = new ModelAndView("/maintenance/selected/doing");

		MaintenanceQuery maintenance = maintenanceServiceQuery.getMaintenance(id);
		ReportOfMaintenance reportOfMaintenance = reportOfMaintenanceService.getReportOfMaintenance(id);

		mav.addObject("maintenance", maintenance);

		return mav;
	}

	@RequestMapping(value = "/maintenances/{id}/done", method = RequestMethod.GET)// GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showSelectedMaintenanceDoneForm(@PathVariable("id") Long id){

		ModelAndView mav = new ModelAndView("/maintenance/selected/done");

		MaintenanceQuery maintenance = maintenanceServiceQuery.getMaintenance(id);
		ReportOfMaintenance report = reportOfMaintenanceService.getReportOfMaintenance(id);

		mav.addObject("maintenance", maintenance);
		mav.addObject("report", report);

		return mav;
	}

	@RequestMapping(value = "/maintenances/{id}/take", method = RequestMethod.GET)//GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public String takeMaintenance(@PathVariable("id") Long id){
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		System.out.println(username);
		maintenanceService.take(id, username);
		return "redirect:/maintenances/doing";
	}
	
	@RequestMapping(value = "/maintenances/doing", method = RequestMethod.GET)//GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showListDoingMaintenances(@RequestParam(required = false, defaultValue = "0") Integer page,
												  @RequestParam(required = false, defaultValue = "20") Integer size){
		
		ModelAndView mav = new ModelAndView("/maintenance/doing");
		Page<MaintenancesQuery> maintenances = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		boolean hasRepairmanRole = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_REPAIRMAN"));

		if(hasRepairmanRole){
			maintenances = maintenanceServiceQuery.listEmployeeMaintenancesWithProgress(username, ProgressStatus.DOING, new PageRequest(page,size, Sort.Direction.ASC, "dateTaken"));
		}else{
			maintenances = maintenanceServiceQuery.listAllMaintenancesWithProgress(ProgressStatus.DOING, new PageRequest(page,size, Sort.Direction.ASC, "dateTaken"));
		}

		Pagination pagination = (maintenances != null) ? new Pagination(maintenances, "/maintenances/doing") : null;
		mav.addObject("maintenances", maintenances);
		mav.addObject("page", pagination);

		return mav;
	}
	
	@RequestMapping(value = "/maintenances/done", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showListDoneMaintenances(@RequestParam(required = false, defaultValue = "0") Integer page,
												 @RequestParam(required = false, defaultValue = "20") Integer size){
		
		ModelAndView mav = new ModelAndView("/maintenance/done");

		Page<MaintenancesQuery> maintenances;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		boolean hasRepairmanRole = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_REPAIRMAN"));

		if(hasRepairmanRole){
			maintenances = maintenanceServiceQuery.listEmployeeMaintenancesWithProgress(username, ProgressStatus.DONE, new PageRequest(page,size, Sort.Direction.ASC, "dateDone"));
		}else{
			maintenances = maintenanceServiceQuery.listAllMaintenancesWithProgress(ProgressStatus.DONE, new PageRequest(page,size, Sort.Direction.ASC, "dateDone"));
		}

		Pagination pagination = (maintenances != null) ? new Pagination(maintenances, "/maintenances/done") : null;
		mav.addObject("maintenances", maintenances);
		mav.addObject("page", pagination);

		return mav;
	}
	
	@RequestMapping(value = "/maintenances/waiting", method = RequestMethod.GET)// GOOD
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView listWaitingMaintenances(@RequestParam(required = false, defaultValue = "0") Integer page,
												@RequestParam(required = false, defaultValue = "20") Integer size){
		
		ModelAndView mav = new ModelAndView("/maintenance/waiting");

		Page<MaintenancesQuery> maintenancesQueries = maintenanceServiceQuery.listAllMaintenancesWithProgressAndPriority(ProgressStatus.WAITING, PriorityStatus.VERY_HIGH, new PageRequest(page,size, Sort.Direction.ASC, "dateScheduled"));

		Pagination pagination = (maintenancesQueries != null) ? new Pagination(maintenancesQueries, "/maintenances/waiting") : null;

		mav.addObject("page", pagination);
		mav.addObject("maintenances", maintenancesQueries);
		
		return mav;
	}

	@RequestMapping(value = "/maintenances/{id}", method = RequestMethod.GET)// TODO; do zrobienia
	@PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
	public ModelAndView showSelectedMaintenance(@PathVariable("id") Long id){

		ModelAndView mav = new ModelAndView("/maintenance/selected");

		Maintenance maintenance = maintenanceRepository.findOne(id);
		List<TriggeredRule> rules = fuzzyLogicService.getTriggeredRules(maintenance);

		mav.addObject("maintenance", maintenance);
		mav.addObject("rules", rules);
		mav.addObject("report", (maintenance != null) ? maintenance.getReport(): null);

		return mav;
	}

}
