package pl.inzynierka.Account.Delete;

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
import pl.inzynierka.User.Domain.Role;
import pl.inzynierka.User.Domain.User;
import pl.inzynierka.User.Query.UserServiceQuery;
import pl.inzynierka.User.Repository.UserRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeleteAccountTests {

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
    User user;

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

        user = new User("first name", "last name", "testUser@test.test", "password123", Role.ROLE_ADMIN);
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void deleteUsersSuccess() throws Exception{

        when(userRepository.findOne(1L)).thenReturn(user);
        mockMvc.perform(
                delete("/users/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
        verify(userRepository, times(1)).findOne(1L);
    }

    @Test
    @WithMockUser(roles = {"REPAIRMAN", "ADMIN"})
    public void deleteUsersFail() throws Exception{

        when(userRepository.findOne(1L)).thenReturn(null);
        mockMvc.perform(
                delete("/users/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(userRepository, times(1)).findOne(1L);
    }

}
