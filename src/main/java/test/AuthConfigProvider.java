package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosClient;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by wjurasz on 18.07.17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AuthConfigProvider extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(spnegoEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        spnegoAuthenticationProcessingFilter(authenticationManagerBean),
                        BasicAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth, KerberosAuthenticationProvider kerberosAuthenticationProvider, KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider) throws Exception {
        auth.authenticationProvider(kerberosAuthenticationProvider)
                .authenticationProvider(kerberosServiceAuthenticationProvider);
    }


    @Bean
    @Autowired
    public KerberosAuthenticationProvider kerberosAuthenticationProvider(DummyUserDetailsService dummyUserDetailsService) {
        KerberosAuthenticationProvider provider = new KerberosAuthenticationProvider();
        SunJaasKerberosClient client = new SunJaasKerberosClient();
        client.setDebug(true);
        provider.setKerberosClient(client);
        provider.setUserDetailsService(dummyUserDetailsService);
        return provider;
    }


    @Bean
    @Autowired
    public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider(SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator, DummyUserDetailsService dummyUserDetailsService) {
        KerberosServiceAuthenticationProvider provider =
                new KerberosServiceAuthenticationProvider();
        provider.setTicketValidator(sunJaasKerberosTicketValidator);
        provider.setUserDetailsService(dummyUserDetailsService);
        return provider;
    }

    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
        SunJaasKerberosTicketValidator ticketValidator =
                new SunJaasKerberosTicketValidator();
        ticketValidator.setServicePrincipal("HTTP/pcbe15493.dyndns.cern.ch@PCBE15493.DYNDNS.CERN.CH");
        ticketValidator.setKeyTabLocation(new FileSystemResource("/home/wjurasz/IdeaProjects/simple-rest-app-test/cern.keytab"));
        ticketValidator.setDebug(true);
        return ticketValidator;
    }

    public SpnegoEntryPoint spnegoEntryPoint() {
        return new SpnegoEntryPoint();
    }

    @Bean
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter(
            AuthenticationManager authenticationManager) {
        SpnegoAuthenticationProcessingFilter filter =
                new SpnegoAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }


    @Bean
    public DummyUserDetailsService dummyUserDetailsService() {
        return new DummyUserDetailsService();
    }

}
