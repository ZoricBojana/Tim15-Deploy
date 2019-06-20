package rs.travel.bookingWithEase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.travel.bookingWithEase.security.TokenUtils;
import rs.travel.bookingWithEase.security.auth.TokenAuthenticationFilter;
import rs.travel.bookingWithEase.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// Definisemo nacin autentifikacije
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	TokenUtils tokenUtils;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
            .antMatchers("/users/confirm").permitAll()
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/branchs").permitAll()

			.antMatchers("/vehicles/{id}/reservations").permitAll()
	
			.antMatchers("/users/inviteFriends").permitAll()
			.antMatchers("/users/friends/**").permitAll()
			.antMatchers("/seats/**").permitAll()
			
			.antMatchers("/flightReservation/**").permitAll()
			.antMatchers("/passengers/**").permitAll()
			.antMatchers("/users/sendMailReservation/**").permitAll()
		
			.anyRequest().authenticated().and()
			//.formLogin()
			//.loginPage("/login.html").permitAll().and()
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);
		
		http.csrf().disable();
	}
	
	// Generalna bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/users/confirm-account");
		web.ignoring().antMatchers(HttpMethod.POST, "/users/confirm-account");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js", "/images/**");

		web.ignoring().antMatchers(HttpMethod.GET, "/branchs");

		web.ignoring().antMatchers(HttpMethod.GET, "/vehicles/{id}/reservations");
	
		
		web.ignoring().antMatchers(HttpMethod.POST, "/flights/search");
		web.ignoring().antMatchers(HttpMethod.GET, "/vehicles");
		web.ignoring().antMatchers(HttpMethod.GET, "/vehicleReservations");
		
		web.ignoring().antMatchers(HttpMethod.GET, "/seats/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/users/friends/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/users/inviteFriends");		
		web.ignoring().antMatchers(HttpMethod.GET, "/discounts");
		web.ignoring().antMatchers(HttpMethod.POST, "/users/inviteFriends/");
		web.ignoring().antMatchers(HttpMethod.GET, "/users/sendMailReservation/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/flightReservation/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/users/confirm-invite");
		web.ignoring().antMatchers(HttpMethod.POST, "/users/confirm-invite");
		web.ignoring().antMatchers(HttpMethod.GET, "/passengers/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/passengers/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/quickFlightReservation/**");
		web.ignoring().antMatchers(HttpMethod.POST, "/quickFlightReservation/**");
		
		// sigurno treba ovde
		web.ignoring().antMatchers(HttpMethod.POST, "/users/registration");
		web.ignoring().antMatchers(HttpMethod.GET, "/hotels/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/rentacars/**");
		web.ignoring().antMatchers("/rentacars/search");
		web.ignoring().antMatchers(HttpMethod.POST, "/rentacars/vehicles/search");
		web.ignoring().antMatchers(HttpMethod.GET, "/flights/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/airlines/**");
		web.ignoring().antMatchers(HttpMethod.POST,"/hotels/search");
		web.ignoring().antMatchers(HttpMethod.POST, "/airlines/search");
		web.ignoring().antMatchers(HttpMethod.POST,"/hotels/rooms");
		web.ignoring().antMatchers(HttpMethod.POST, "/hotels/{hotelId}/quickRoomReservations/search");
	}
}
