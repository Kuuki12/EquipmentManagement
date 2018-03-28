package pl.inzynierka.Maintenance.Security;

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
import pl.inzynierka.FuzzyLogic.Service.FuzzyLogicService;
import pl.inzynierka.Maintenance.Command.MaintenanceService;
import pl.inzynierka.Maintenance.Controller.MaintenanceController;
import pl.inzynierka.Maintenance.Query.MaintenanceServiceQuery;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Repairman.Repository.RepairmanRepository;
import pl.inzynierka.Report.Command.ReportOfMaintenanceService;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ListMaintenancesWithProgressDoingTest {

    @Autowired FilterChainProxy springSecurityFilterChain;
    @Mock MaintenanceService maintenanceService;
    @Mock ReportOfMaintenanceService reportOfMaintenanceService;
    @Mock MaintenanceServiceQuery maintenanceServiceQuery;
    @Mock CommandGateway commandGateway;
    @Mock FuzzyLogicService fuzzyLogicService;
    @Mock MaintenanceRepository maintenanceRepository;
    @Mock RepairmanRepository repairmanRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new MaintenanceController(maintenanceService,
                                reportOfMaintenanceService,
                                maintenanceServiceQuery,
                                commandGateway,
                                fuzzyLogicService,
                                maintenanceRepository,
                                repairmanRepository))
                .setViewResolvers(viewResolver)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN"})
    public void test_ListMaintenancesWithProgressDoing_ReturnAuthorizationForRepairman_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/doing"))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void test_ListMaintenancesWithProgressDoing_ReturnAuthorizationForAdmin_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/doing"))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    public void test_ListMaintenancesWithProgressDoing_ReturnAuthorizationForClient_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/doing"))
                .andDo(print())
                .andExpect(status().is(403));

    }

    @Test
    public void test_ListMaintenancesWithProgressDoing_ReturnAuthorizationForGuest_Success() throws Exception {

        mockMvc.perform(
                get("/maintenances/1/doing"))
                .andDo(print())
                .andExpect(status().is(302));

    }

}
