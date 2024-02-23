package com.hackaton.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.hackaton.monitor.service.ServiceMonitor;

@Controller
public class MonitorController {

    private final ServiceMonitor serviceMonitor;

    public MonitorController(ServiceMonitor serviceMonitor) {
        this.serviceMonitor = serviceMonitor;
    }

    @GetMapping("/monitor")
    public String monitor(Model model) {
        model.addAttribute("services", serviceMonitor.getServiceStatusList());
        return "monitor";
    }
}