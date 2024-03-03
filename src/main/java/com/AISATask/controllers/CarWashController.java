package com.AISATask.controllers;

import com.AISATask.models.CarWash;
import com.AISATask.services.CarWashService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class CarWashController {

    private final CarWashService carWashService;

    @PostMapping
    public CarWash addService(@RequestBody CarWash service) {
        return carWashService.addService(service);
    }

    @DeleteMapping("/{serviceId}")
    public void deleteService(@PathVariable Long serviceId) {
        carWashService.deleteService(serviceId);
    }

    @GetMapping
    public List<CarWash> getAllServices() {
        return carWashService.getAllServices();
    }


}
