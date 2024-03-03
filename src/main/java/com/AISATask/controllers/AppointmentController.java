package com.AISATask.controllers;

import com.AISATask.DTO.AppointmentDto;
import com.AISATask.models.Appointment;
import com.AISATask.models.CarWash;
import com.AISATask.models.User;
import com.AISATask.repos.UserRepository;
import com.AISATask.services.AppointmentService;
import com.AISATask.services.CarWashService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;
    private final CarWashService carWashService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentDto appointmentDto) {
        // Получение текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        System.out.println("Current username: " + currentUsername);
        // Поиск пользователя по имени пользователя
        User user = userRepository.findByUsername(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Найти услугу по ID
        CarWash service = carWashService.findById(appointmentDto.getServiceId());

        // Создание новой записи
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setService(service);
        appointment.setAppointmentTime(appointmentDto.getAppointmentTime());

        Appointment savedAppointment = appointmentService.save(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @GetMapping("/user/{userId}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }
}
