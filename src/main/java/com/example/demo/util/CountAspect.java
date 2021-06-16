package com.example.demo.util;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CountAspect {
    private final MeterRegistry registry;

    @AfterReturning(pointcut = "@annotation(count)", returning = "result")
    public void count(Count count, boolean result) {
        if (result) {
            registry.counter(count.counterName()).increment();
        }
    }
}
