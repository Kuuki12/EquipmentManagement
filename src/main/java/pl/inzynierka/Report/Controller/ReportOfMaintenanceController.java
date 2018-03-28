package pl.inzynierka.Report.Controller;

import java.io.IOException;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Exceptions.MaintenanceDoesntExist;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Report.Command.AddReportCommand;
import pl.inzynierka.Report.Command.UpdateReportCommand;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import pl.inzynierka.Report.Command.ReportOfMaintenanceService;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class ReportOfMaintenanceController {

	ReportOfMaintenanceService reportOfMaintenanceService;
	MaintenanceRepository maintenanceRepository;
	CommandGateway commandGateway;
	ReportOfMaintenanceRepository reportOfMaintenanceRepository;
	EquipmentRepository equipmentRepository;

	@Autowired
	public ReportOfMaintenanceController(ReportOfMaintenanceService reportOfMaintenanceService,
										 MaintenanceRepository maintenanceRepository,
										 CommandGateway commandGateway,
										 ReportOfMaintenanceRepository reportOfMaintenanceRepository,
										 EquipmentRepository equipmentRepository) {

		this.reportOfMaintenanceService = reportOfMaintenanceService;
		this.maintenanceRepository = maintenanceRepository;
		this.commandGateway = commandGateway;
		this.reportOfMaintenanceRepository = reportOfMaintenanceRepository;
		this.equipmentRepository = equipmentRepository;
	}

	@RequestMapping(value = "/maintenances/{IDMaintenance}/reports/create", method = RequestMethod.GET)//GOOD
	public ModelAndView showCreateReportOfMaintenanceForm(@PathVariable("IDMaintenance")Long IDMaintenance){
		
		ModelAndView mav = new ModelAndView("/reportOfMaintenance/create");

		mav.addObject("report", new AddReportCommand());
		
		return mav;
	}
	
	@RequestMapping(value = "/maintenances/{IDMaintenance}/reports/create", method = RequestMethod.POST)//GOOD
	public String createReportOfMaintenance(
			@PathVariable("IDMaintenance")Long IDMaintenance,
			@ModelAttribute("report") @Valid AddReportCommand addReportCommand, BindingResult result){

		if(result.hasErrors()) return "/reportOfMaintenance/create";

		addReportCommand.setidMaintenance(IDMaintenance);
		commandGateway.send(addReportCommand);

		return "redirect:/maintenances/doing";
	}

	@RequestMapping(value = "/download/report/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("id")Long id, HttpServletResponse response)throws IOException{

		ReportOfMaintenance report = reportOfMaintenanceRepository.findOne(id);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=Raport-"+report.getName()+".pdf");
		response.getOutputStream().write(report.getFile());
	}

	@RequestMapping(value = "/maintenances/{serialCodeOfMaintenance}/reports/{serialCodeOfReport}/update", method = RequestMethod.GET)
	public ModelAndView showUpdateReportOfMaintenanceForm(
			@PathVariable("serialCodeOfMaintenance")Long serialCodeOfMaintenance,
			@PathVariable("serialCodeOfReport")String serialCodeOfReport){
		
		ModelAndView mav = new ModelAndView("/reportOfMaintenance/update");
		
		if(serialCodeOfMaintenance != null && serialCodeOfReport != null){
			mav.addObject("report", reportOfMaintenanceService.getReportOfMaintenance(serialCodeOfMaintenance));
		}
		
		return mav;
	}

	@RequestMapping(value = "/maintenances/{serialCodeOfMaintenance}/reports/{serialCodeOfReport}/update", method = RequestMethod.POST)
	public String updateReportOfMaintenance(
			@PathVariable("serialCodeOfMaintenance")Long serialCodeOfMaintenance,
			@PathVariable("serialCodeOfReport")Long serialCodeOfReport,
			@ModelAttribute("report") @Valid UpdateReportCommand updateReportCommand, BindingResult result){

		if(result.hasErrors()) return "/reportOfMaintenance/update";

		updateReportCommand.setIdMaintenance(serialCodeOfMaintenance);
		updateReportCommand.setIdReport(serialCodeOfReport);

		commandGateway.send(updateReportCommand);

		return "redirect:/maintenances/done";
	}
	
}
