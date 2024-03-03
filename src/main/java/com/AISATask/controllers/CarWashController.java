package com.AISATask.controllers;

import com.AISATask.models.CarWash;
import com.AISATask.services.CarWashService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Tag(name = "ServicesController", description = "Операции для управления услугами на автомойке")
public class CarWashController {

    private final CarWashService carWashService;

    @PostMapping
    @Operation(summary = "Добавить новую услугу мойки",
            description = "Создает и возвращает новую услугу мойки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Услуга успешно добавлена"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные услуги")
            })
    public CarWash addService(@RequestBody CarWash service) {
        return carWashService.addService(service);
    }

    @DeleteMapping("/{serviceId}")
    @Operation(summary = "Удалить услугу мойки",
            description = "Удаляет услугу мойки по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Услуга успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Услуга не найдена")
            })
    public void deleteService(@PathVariable Long serviceId) {
        carWashService.deleteService(serviceId);
    }

    @GetMapping
    @Operation(summary = "Получить список всех услуг мойки",
            description = "Возвращает список всех доступных услуг мойки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список услуг получен"),
                    @ApiResponse(responseCode = "204", description = "Услуги отсутствуют")
            })
    public List<CarWash> getAllServices() {
        return carWashService.getAllServices();
    }


}
