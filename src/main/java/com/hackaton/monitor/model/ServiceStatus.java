package com.hackaton.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ServiceStatus implements Serializable {

    @Serial
    private static final long serialVersionUID = -4560822359946529671L;

    private String url;
    private boolean active;
    private int statusCode;
}
