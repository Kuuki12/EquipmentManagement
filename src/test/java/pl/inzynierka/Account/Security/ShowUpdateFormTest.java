package pl.inzynierka.Account.Security;

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
import pl.inzynierka.User.Command.UserServiceCommand;
import pl.inzynierka.User.Controller.AccountsController;
import pl.inzynierka.User.Query.UserServiceQuery;
import pl.inzynierka.User.Repository.UserRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShowUpdateFormTest {

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
    public void setup(){

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
    public void testShowUpdateUserFormAuthorizationForRepairmanFailure() throws Exception{

        mockMvc.perform(
                get("/accounts/1/update"))
                .andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testShowUpdateUserFormAuthorizationForAdminSuccess() throws Exception{

        mockMvc.perform(
                get("/accounts/1/update"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    public void testShowUpdateUserFormAuthorizationForClientFailure() throws Exception{

        mockMvc.perform(
                get("/accounts/1/update"))
                .andDo(print())
                .andExpect(status().is(403));
    }
}
