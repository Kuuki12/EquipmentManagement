package pl.inzynierka.Account.Security;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
import pl.inzynierka.Report.Command.ReportOfMaintenanceService;
import pl.inzynierka.Report.Domain.ReportOfMaintenance;
import pl.inzynierka.Report.Repository.ReportOfMaintenanceRepository;
import pl.inzynierka.User.Command.AddUserCommand;
import pl.inzynierka.User.Command.UserServiceCommand;
import pl.inzynierka.User.Controller.AccountsController;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Query.UserServiceQuery;
import pl.inzynierka.User.Repository.UserRepository;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShowCreateFormTest {

    @Autowired
    FilterChainProxy springSecurityFilterChain;
    @Mock
    CommandGateway commandGateway;
    @Mock
    UserRepository userRepository;
    @Mock
    UserServiceCommand userServiceCommand;
    @Mock
    UserServiceQuery userServiceQuery;

    MockMvc mockMvc;

    @Before
    public void setup() throws Exception{

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders
                .standaloneSetup(new AccountsController(userServiceCommand, commandGateway, userServiceQuery, userRepository))
                .setViewResolvers(viewResolver)
                .apply(springSecurity(springSecurityFilterChain))
                .build();

    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN"})
    public void testShowCreateUserFormAuthorizationForRepairmanSuccess() throws Exception{

        mockMvc.perform(
                get("/users/create"))
                .andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testShowCreateUserFormAuthorizationForAdminSuccess() throws Exception{

        mockMvc.perform(
                get("/users/create"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    public void testShowCreateUserFormAuthorizationForClientFailure() throws Exception{

        mockMvc.perform(
                get("/users/create"))
                .andDo(print())
                .andExpect(status().is(403));
    }
}
