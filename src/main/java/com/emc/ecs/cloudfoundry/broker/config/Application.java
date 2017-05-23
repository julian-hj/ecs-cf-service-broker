package com.emc.ecs.cloudfoundry.broker.config;

import com.emc.ecs.cloudfoundry.broker.EcsManagementClientException;
import com.emc.ecs.cloudfoundry.broker.EcsManagementResourceNotFoundException;
import com.emc.ecs.cloudfoundry.broker.repository.ServiceInstanceBindingRepository;
import com.emc.ecs.cloudfoundry.broker.repository.ServiceInstanceRepository;
import com.emc.ecs.cloudfoundry.broker.service.EcsService;
import com.emc.ecs.cloudfoundry.broker.service.EcsServiceInstanceBindingService;
import com.emc.ecs.cloudfoundry.broker.service.EcsServiceInstanceService;
import com.emc.ecs.management.sdk.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.servicebroker.model.BrokerApiVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.URISyntaxException;
import java.net.URL;

@EnableAutoConfiguration
@ComponentScan
public class Application {

//    static {
//        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//                new javax.net.ssl.HostnameVerifier(){
//
//                    public boolean verify(String hostname,
//                                          javax.net.ssl.SSLSession sslSession) {
//                        return true;
//                    }
//                });
//    }
//
    @Autowired
    private BrokerConfig broker;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Connection ecsConnection() {
	if (broker.getCertificate() != null) {
        URL certificate = Thread.currentThread().getContextClassLoader()
                .getResource(broker.getCertificate());
        return new Connection(broker.getManagementEndpoint(),
                broker.getUsername(), broker.getPassword(), certificate);
	} else {
		return new Connection(broker.getManagementEndpoint(),
                broker.getUsername(), broker.getPassword());
	}
    }

    @Bean
    public BrokerApiVersion brokerApiVersion() {
        return new BrokerApiVersion(broker.getBrokerApiVersion());
    }

    @Bean
    public EcsService ecsService() throws EcsManagementClientException,
            EcsManagementResourceNotFoundException {
        return new EcsService();
    }

    @Bean
    public EcsServiceInstanceBindingService ecsServiceInstanceBindingService()
            throws EcsManagementClientException,
            EcsManagementResourceNotFoundException, URISyntaxException {
        return new EcsServiceInstanceBindingService();
    }

    @Bean
    public ServiceInstanceRepository serviceInstanceRepository()
            throws EcsManagementClientException,
            EcsManagementResourceNotFoundException, URISyntaxException {
        return new ServiceInstanceRepository();
    }

    @Bean
    public EcsServiceInstanceService ecsServiceInstanceService()
            throws EcsManagementClientException,
            EcsManagementResourceNotFoundException, URISyntaxException {
        return new EcsServiceInstanceService();
    }

    @Bean
    public ServiceInstanceBindingRepository serviceInstanceBindingRepository()
            throws EcsManagementClientException,
            EcsManagementResourceNotFoundException, URISyntaxException {
        return new ServiceInstanceBindingRepository();
    }

    public BrokerConfig getBroker() {
        return broker;
    }

    public void setBroker(BrokerConfig broker) {
        this.broker = broker;
    }
}