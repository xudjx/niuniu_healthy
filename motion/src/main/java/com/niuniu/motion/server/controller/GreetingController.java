package com.niuniu.motion.server.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.niuniu.motion.dto.GreetingDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public GreetingDTO greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingDTO(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/hello")
    public GreetingDTO hello(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingDTO(counter.incrementAndGet(),
                String.format(template, name));
    }
}
