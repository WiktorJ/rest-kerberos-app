package test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.kerberos.authentication.sun.GlobalSunJaasKerberosConfig;

/**
 * <p>
 * <b> Additional Configuration for Kerberos. Specifically location of the
 * krb5.conf file This configuration must be in a separate configuration file
 * to the main kerbneros config to prevent instantiation ordering issues</b>
 * </p>
 */

//@Configuration
public class GlobalSecurityConfig {


//    @Bean
    public GlobalSunJaasKerberosConfig globalSunJaasKerberosConfig() {
        GlobalSunJaasKerberosConfig globalConfig = new GlobalSunJaasKerberosConfig();
        globalConfig.setDebug(true);
        globalConfig.setKrbConfLocation("/etc/krb5.conf");
        return globalConfig;
    }
}