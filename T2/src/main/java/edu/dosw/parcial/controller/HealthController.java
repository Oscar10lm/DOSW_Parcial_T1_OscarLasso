package edu.dosw.parcial.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Verificación de salud del microservicio")
public class HealthController {

    @GetMapping
    @Operation(summary = "Comprobar estado del servicio", description = "Retorna el estado UP y nombre del microservicio")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "DOSW-ParcialT2");
    }
}
