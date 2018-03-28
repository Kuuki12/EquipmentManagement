package pl.inzynierka.Equipment.Security;

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
import pl.inzynierka.Equipment.Command.EquipmentService;
import pl.inzynierka.Equipment.Controller.EquipmentController;
import pl.inzynierka.Equipment.Query.EquipmentServiceQuery;
import pl.inzynierka.Equipment.Repository.EquipmentRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShowUpdateEquipmentTest {

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

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/resources/templates");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new EquipmentController(equipmentService, equipmentServiceQuery, commandGateway, equipmentRepository))
                .setViewResolvers(viewResolver)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN"})
    public void testShowCreateEquipmentFormAuthorizationForRepairmanSuccess() throws Exception {

        mockMvc.perform(
                get("/equipments/create"))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testShowCreateEquipmentFormAuthorizationForAdminSuccess() throws Exception {

        mockMvc.perform(
                get("/equipments/create"))
                .andDo(print())
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    public void testShowCreateEquipmentFormAuthorizationForClientSuccess() throws Exception {

        mockMvc.perform(
                get("/equipments/create"))
                .andDo(print())
                .andExpect(status().is(403));

    }

    @Test
    public void testShowCreateEquipmentFormAuthorizationForGuestSuccess() throws Exception {

        mockMvc.perform(
                get("/equipments/create"))
                .andDo(print())
                .andExpect(status().is(302));

    }

}
