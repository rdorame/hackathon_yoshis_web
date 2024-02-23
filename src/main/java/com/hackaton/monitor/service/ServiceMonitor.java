package com.hackaton.monitor.service;

import com.hackaton.monitor.properties.ServiceProperties;
import lombok.Getter;
import com.hackaton.monitor.model.ServiceStatus;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ServiceMonitor {

    private final WebClient webClient;
    private final ServiceProperties serviceProperties;

    public ServiceMonitor(WebClient webClient,
                          ServiceProperties serviceProperties) {
        this.webClient = webClient;
        this.serviceProperties = serviceProperties;
    }

    public Mono<ServiceStatus> checkService(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .map(response -> new ServiceStatus(url, true, response.getStatusCode().value()))
                .onErrorResume(e -> Mono.just(new ServiceStatus(url, false, HttpStatus.SERVICE_UNAVAILABLE.value())));
    }

    @Getter
    private final List<ServiceStatus> serviceStatusList = new CopyOnWriteArrayList<>();

    @Scheduled(fixedRate = 15000)
    public void monitorServices() {
        List<String> urls = serviceProperties.getUrls();
        Flux.fromIterable(urls)
                .flatMap(this::checkService)
                .collectList()
                .doOnNext(list -> {
                    serviceStatusList.clear();
                    serviceStatusList.addAll(list);
                })
                .subscribe();
    }

}
