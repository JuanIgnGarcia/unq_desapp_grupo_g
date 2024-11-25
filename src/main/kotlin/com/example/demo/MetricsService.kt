package com.example.demo

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsService(private val meterRegistry: MeterRegistry) {

    init {
        meterRegistry.gauge("custom_metric", 1.0) // Puedes añadir métricas personalizadas
    }
}