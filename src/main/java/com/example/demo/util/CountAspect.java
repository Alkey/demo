package com.example.demo.util;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CountAspect {
    private static final String COUNTER_NAME = "geometry_count";
    private final MeterRegistry registry;

    @Around("@annotation(Count)")
    public Object count(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        if ((boolean) proceed) {
            registry.counter(COUNTER_NAME).increment();
        }
        return proceed;
    }
}
