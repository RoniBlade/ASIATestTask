package com.AISATask.controllers;

import com.AISATask.DTO.AppointmentDto;
import com.AISATask.models.Appointment;
import com.AISATask.models.CarWash;
import com.AISATask.models.User;
import com.AISATask.repos.UserRepository;
import com.AISATask.services.AppointmentService;
import com.AISATask.services.CarWashService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "AppointmentController", description = "Операции для управления записями")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;
    private final CarWashService carWashService;

    @PostMapping
    @Operation(summary = "Создать запись на мойку",
            description = "Создает новую запись на мойку для аутентифицированного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запись успешно создана",
                            content = @Content(schema = @Schema(implementation = Appointment.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь или услуга не найдены"),
                    @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
            })
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
    @Operation(summary = "Получить записи пользователя",
            description = "Возвращает список всех записей на мойку для указанного пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Записи найдены",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
                    @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
            })
    public List<Appointment> getAppointmentsByUserId(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }
}
