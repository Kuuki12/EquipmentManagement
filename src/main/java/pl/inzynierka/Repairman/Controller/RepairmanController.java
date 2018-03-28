package pl.inzynierka.Repairman.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Command.MaintenanceService;

@Controller
public class RepairmanController {
	
	@Autowired
	public MaintenanceService maintenanceService;
	
	@RequestMapping(value = "/repairmans/{serialCode}", method = RequestMethod.GET)
	public ModelAndView showRepairmanForm(@PathVariable("serialCode") String serialCode){
		
		ModelAndView mav = new ModelAndView("/employee/selected");
		
//		if(serialCode != null){
//			Repairman repairman = new Repairman();
////			TODO: find employee in DB and return object. Late add this object to object "mav"
//			mav.addObject("employee", repairman);
//		}
		
		return null;
	}
	
	@RequestMapping(value = "/repairmans", method = RequestMethod.GET)
	public ModelAndView showAllRepairmanForm(){
	
//		ModelAndView mav = new ModelAndView("/employee/list");
//
//		List<RepairmanInformationSO> repairmans = new ArrayList<RepairmanInformationSO>();
//
////		TODO: find all employee and return them object to array list employees
//
//		mav.addObject("employees" , repairmans);
		
		return null;
	}
	
	@RequestMapping(value = "/repairmans/maintenances/doing")
	public ModelAndView listRepairmanActualDoingMaintenances(){
		
		ModelAndView mav = new ModelAndView("/maintenance/doingByRepairman");
		List<Maintenance> maintenances = new ArrayList<Maintenance>();
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		if(username != null){
			maintenances.addAll(maintenanceService.listAllRepairmanMaintenancesWithProgress(username,"W trakcie"));
		}else{
			//TODO: throw exception
		}
		
		mav.addObject("maintenances", maintenances);
		
		return mav;
	}
	
	@RequestMapping(value = "/repairmans/maintenances/done")
	public ModelAndView listRepairmanActualDoneMaintenances(){
		
		ModelAndView mav = new ModelAndView("/maintenance/doneByRepairman");
		List<Maintenance> maintenances = new ArrayList<Maintenance>();
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		if(username != null){
			maintenances.addAll(maintenanceService.listAllRepairmanMaintenancesWithProgress(username,"Zrobione"));
		}else{
			//TODO: throw exception
		}
		
		mav.addObject("maintenances", maintenances);
		
		return mav;
	}
	
	@RequestMapping(value = "/repairmans/{numberID}/maintenances/{progress}", method = RequestMethod.GET)
	public void listRepairmanHistoryMaintenances(
			@PathVariable("numberID")String numberID, 
			@PathVariable("progress") String progress ){
		
	}
	
}
