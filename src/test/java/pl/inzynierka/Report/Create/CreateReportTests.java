package pl.inzynierka.Report.Create;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.inzynierka.Client.Domain.Client;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.FuzzyLogic.Service.FuzzyLogicService;
import pl.inzynierka.Image.Domain.Image;
import pl.inzynierka.Maintenance.Command.MaintenanceService;
import pl.inzynierka.Maintenance.Domain.Maintenance;
import pl.inzynierka.Maintenance.Query.MaintenanceServiceQuery;
import pl.inzynierka.Maintenance.Repository.MaintenanceRepository;
import pl.inzynierka.Report.Command.AddReportCommand;
import pl.inzynierka.Report.Command.ReportOfMaintenanceService;
import pl.inzynierka.Report.Controller.ReportOfMaintenanceController;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Repository.UserRepository;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CreateReportTests {

    @Autowired
    FilterChainProxy springSecurityFilterChain;
    @Mock
    CommandGateway commandGateway;
    @Mock
    MaintenanceService maintenanceService;
    @Mock
    ReportOfMaintenanceService reportOfMaintenanceService;
    @Mock
    MaintenanceServiceQuery maintenanceServiceQuery;
    @Mock
    FuzzyLogicService fuzzyLogicService;
    @Mock
    MaintenanceRepository maintenanceRepository;
    @Mock
    EquipmentRepository equipmentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ReportOfMaintenanceRepository reportOfMaintenanceRepository;


    MockMvc mockMvc;
    MultipartFile image;
    User user;
    Client client;
    Equipment equipment;
    Maintenance maintenance;
    ReportOfMaintenance report;

    @Before
    public void setup() throws Exception{

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates");
        viewResolver.setSuffix(".html");


        mockMvc = MockMvcBuilders
                .standaloneSetup(new ReportOfMaintenanceController(reportOfMaintenanceService, maintenanceRepository, commandGateway, reportOfMaintenanceRepository, equipmentRepository))
                .setViewResolvers(viewResolver)
                .apply(springSecurity(springSecurityFilterChain))
                .build();

        Resource resource = new ClassPathResource("/static/TestRaport.pdf");

        image = new MockMultipartFile("TestRaport.pdf", "TestRaport.pdf", "application/pdf", resource.getInputStream());
        user = new User("first name", "last name", "testUser@test.test", "password123", Role.ROLE_ADMIN);
        client = new Client(user, null);
        equipment = new Equipment("name", new Image(image.getBytes()), client, 45, LocalDate.now(), 10, "description", "serialNumber", LocalDate.now().minusDays(30L));
        maintenance = new Maintenance("name", LocalDate.now(), equipment);
        report = new ReportOfMaintenance("name", "description", image.getBytes());

    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void createReportSuccess() throws Exception{

        AddReportCommand addReportCommand = new AddReportCommand("name", "description", image);

        mockMvc.perform(
                post("/maintenances/{IDMaintenance}/reports/create", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("report", addReportCommand))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/maintenances/doing"));
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void createReportFail() throws Exception{

        AddReportCommand addReportCommand = new AddReportCommand("", "description", image);

        mockMvc.perform(
                post("/maintenances/{IDMaintenance}/reports/create", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("report", addReportCommand))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("report", "name", "NotEmpty"));
    }

}
