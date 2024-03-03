package com.AISATask.controllers;

import com.AISATask.models.Appointment;
import com.AISATask.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/user/{userId}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }
}
