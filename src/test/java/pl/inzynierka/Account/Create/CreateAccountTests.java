package pl.inzynierka.Account.Create;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CreateAccountTests {

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
    public void setup() throws Exception {

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
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void createUsersSuccess() throws Exception {

        AddUserCommand addUserCommand = new AddUserCommand("testUser@test.test", "password123", "first name", "last name", Role.ROLE_ADMIN);

        mockMvc.perform(
                post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("user", addUserCommand))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void createUsersFail() throws Exception {

        AddUserCommand addUserCommand = new AddUserCommand("", "password123", "first name", "last name", Role.ROLE_ADMIN);

        mockMvc.perform(
                post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("user", addUserCommand))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("user", "email", "NotEmpty"));
    }
}

