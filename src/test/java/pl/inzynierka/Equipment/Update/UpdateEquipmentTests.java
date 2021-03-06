package pl.inzynierka.Equipment.Update;

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
import pl.inzynierka.Client.Repository.ClientRepository;
import pl.inzynierka.Equipment.Command.EquipmentService;
import pl.inzynierka.Equipment.Command.UpdateEquipmentCommand;
import pl.inzynierka.Equipment.Controller.EquipmentController;
import pl.inzynierka.Equipment.Domain.Equipment;
import pl.inzynierka.Equipment.Query.EquipmentServiceQuery;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;
import pl.inzynierka.Image.Domain.Image;
import pl.inzynierka.Image.Repository.ImageRepository;
import pl.inzynierka.User.Command.UserServiceCommand;
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Query.UserServiceQuery;
import pl.inzynierka.User.Repository.UserRepository;
import pl.inzynierka.validator.UserNotExistsConstraint;
import pl.inzynierka.validator.UserNotExistsValidator;

import javax.validation.Validator;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UpdateEquipmentTests {

    @Autowired
    FilterChainProxy springSecurityFilterChain;
    @Mock
    CommandGateway commandGateway;
    @Mock
    EquipmentService equipmentService;
    @Mock
    EquipmentServiceQuery equipmentServiceQuery;
    @Mock
    EquipmentRepository equipmentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ImageRepository imageRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    UserNotExistsConstraint userNotExistsConstraint;
    @Mock
    UserNotExistsValidator userNotExistsValidator;

    MockMvc mockMvc;
    MultipartFile image;
    User user;
    Client client;
    Equipment equipment;

    @Before
    public void setUp() throws Exception{

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates");
        viewResolver.setSuffix(".html");

        equipmentServiceQuery = new EquipmentServiceQuery(equipmentRepository);
        equipmentService = new EquipmentService(equipmentRepository, imageRepository, userRepository, clientRepository);
        userNotExistsValidator = new UserNotExistsValidator(userRepository);


        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new EquipmentController(equipmentService, equipmentServiceQuery, commandGateway, equipmentRepository))
                .setViewResolvers(viewResolver)
                .apply(springSecurity(springSecurityFilterChain))
                .build();

        Resource resource = new ClassPathResource("/static/img/UserDefaultPicture.png");

        image = new MockMultipartFile("UserDefaultPicture.png", "UserDefaultPicture.png", "image/png", resource.getInputStream());
        user = new User("first name", "last name", "testUser@test.test", "password123", Role.ROLE_ADMIN);
        client = new Client(user, null);
        equipment = new Equipment("name", new Image(image.getBytes()), client, 45, LocalDate.now(), 10, "description", "serialNumber", LocalDate.now().minusDays(30L));

        userNotExistsValidator.initialize(userNotExistsConstraint);
    }


    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void updateEquipmentSuccess() throws Exception{

        UpdateEquipmentCommand updateEquipmentCommand = new UpdateEquipmentCommand("title", image, "testUser@test.test", 50, LocalDate.now(), 10, "description", "serialNumber", LocalDate.now().minusDays(30L));

        when(userRepository.findByUsername("testUser@test.test")).thenReturn(user);

        mockMvc.perform(
                post("/equipments/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("equipment", updateEquipmentCommand))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/equipments"));
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void updateEquipmentFail() throws Exception{

        UpdateEquipmentCommand updateEquipmentCommand = new UpdateEquipmentCommand("", image, "", 50, LocalDate.now(), 10, "description", "serialNumber", LocalDate.now().minusDays(30L));

        mockMvc.perform(
                post("/equipments/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("equipment", updateEquipmentCommand))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrorCode("equipment", "emailOwner", "NotEmpty"));
    }

}
