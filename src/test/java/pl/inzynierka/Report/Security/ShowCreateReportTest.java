package pl.inzynierka.Report.Security;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.FuzzyLogic.Service.FuzzyLogicService;
import pl.inzynierka.Maintenance.Command.MaintenanceService;
import pl.inzynierka.Maintenance.Controller.MaintenanceController;
import pl.inzynierka.Maintenance.Query.MaintenanceServiceQuery;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Repairman.Repository.RepairmanRepository;
import pl.inzynierka.Report.Command.ReportOfMaintenanceService;
import pl.inzynierka.Report.Controller.ReportOfMaintenanceController;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShowCreateReportTest {

    @Autowired FilterChainProxy springSecurityFilterChain;
    @Mock ReportOfMaintenanceService reportOfMaintenanceService;
    @Mock CommandGateway commandGateway;
    @Mock MaintenanceRepository maintenanceRepository;
    @Mock ReportOfMaintenanceRepository reportOfMaintenanceRepository;
    @Mock EquipmentRepository equipmentRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new ReportOfMaintenanceController(reportOfMaintenanceService,
                                maintenanceRepository,
                                commandGateway,
                                reportOfMaintenanceRepository,
                                equipmentRepository))
                .setViewResolvers(viewResolver)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN"})
    public void test_ShowCreateReportForm_AuthorizationForRepairman_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/reports/create"))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void test_ShowCreateReportForm_AuthorizationForAdmin_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/reports/create"))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    public void test_ShowCreateReportForm_AuthorizationForClient_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/reports/create"))
                .andDo(print())
                .andExpect(status().is(403));

    }

    @Test
    public void test_ShowCreateReportForm_AuthorizationForGuest_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/reports/create"))
                .andDo(print())
                .andExpect(status().is(302));

    }

}
