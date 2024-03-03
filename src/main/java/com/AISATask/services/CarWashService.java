package com.AISATask.services;

import com.AISATask.models.CarWash;
import com.AISATask.repos.CarWashRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarWashService {

    private final CarWashRepository carWashRepository;

    @Transactional
    public CarWash addService(CarWash service) {
        return carWashRepository.save(service);
    }

    @Transactional
    public void deleteService(Long serviceId) {
        carWashRepository.deleteById(serviceId);
    }

    public List<CarWash> getAllServices() {
        return carWashRepository.findAll();
    }



}
