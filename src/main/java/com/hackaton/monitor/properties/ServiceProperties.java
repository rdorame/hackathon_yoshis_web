package com.hackaton.monitor.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "service.hp")
public class ServiceProperties {
    private List<String> urls;
}
