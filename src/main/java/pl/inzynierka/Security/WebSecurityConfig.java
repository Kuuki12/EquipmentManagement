package pl.inzynierka.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;

@Configuration
@EnableWebSecurity
@Order(1000)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username, password, enabled from users where username = ?")
//			.authoritiesByUsernameQuery("select username, role from user_roles where username = ?");
			.authoritiesByUsernameQuery("select username, role from users where username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
//				.antMatchers("/equipments").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
				.antMatchers("/users/*").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/users").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/accounts/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/equipments/create", "/equipments/*/update" ).access("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
				.antMatchers( HttpMethod.DELETE,"/equipments/*", "/maintenances/*" ).access("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
				.antMatchers( HttpMethod.GET,"/equipments/*", "/equipments", "/maintenances/*" ).access("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
//				.antMatchers(HttpMethod.GET, "/maintenances/{id}" ).access("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
				.antMatchers("/maintenances/create",
						"/maintenances/*/update",
						"/maintenances/*/delete",
						"/maintenances/*/waiting",
						"/maintenances/*/doing",
						"/maintenances/*/done",
						"/maintenances/*/take",
						"/maintenances/waiting",
						"/maintenances/doing",
						"/maintenances/done",
						"/maintenances/*/reports/create",
						"/maintenances/*/reports/*/update").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")
				.antMatchers("/download/report/*" ).access("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN') or hasRole('ROLE_REPAIRMAN')")

				.antMatchers("/initial").anonymous()//TODO: usunac
				.antMatchers("/account/*").authenticated()
				.antMatchers("/signUp").anonymous()
				.antMatchers("/saveSignUp").anonymous()
				.antMatchers("/signIn").anonymous()
//				.antMatchers("/**").authenticated()
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/signIn")
				.loginPage("/signIn?logout=logout")
				.loginPage("/signIn?error")
				.failureUrl("/signIn?error")
				.defaultSuccessUrl("/equipments")
				.usernameParameter("username")
				.passwordParameter("password")
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/signIn?logout=logout") 
				.and()
			.exceptionHandling()
				.accessDeniedPage("/errors/403")
				.and()
			.csrf().disable();
		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl("/signIn?expired=expired");
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
}
