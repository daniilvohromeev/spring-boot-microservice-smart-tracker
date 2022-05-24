package com.main.springbootcloudtemplatingservice.controllers;


import com.main.springbootcloudtemplatingservice.payload.TaskRequest;
import com.main.springbootcloudtemplatingservice.services.TemplatingService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TemplatingController {

    final TemplatingService service;

    public TemplatingController(TemplatingService service) {
        this.service = service;
    }

    //TODO: сменить параметры на сущность
    @PostMapping("/test")
    String templateCreator(@RequestBody TaskRequest task) {

        //TODO: сделать красиво, а не вот это
//        List<String> temp = new ArrayList<>();
//        temp.add(task.getUsername());
//        temp.add(task.getBeginDate());
//        temp.add(task.getEndDate());
//        temp.add(task.getDailyDistance());

        return service.createTemplate(task);
    }
}
