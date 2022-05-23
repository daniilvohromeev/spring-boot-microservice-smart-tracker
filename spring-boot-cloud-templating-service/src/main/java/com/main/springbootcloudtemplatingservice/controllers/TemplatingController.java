package com.main.springbootcloudtemplatingservice.controllers;


import com.main.springbootcloudtemplatingservice.services.TemplatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TemplatingController {

    @Autowired
    TemplatingService service;

    //TODO: сменить параметры на сущность
    @PostMapping("/test")
    String templateCreator(@RequestParam String nickname, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String kilometers){

        //TODO: сделать красиво, а не вот это
        List<String> temp = new ArrayList<>();
        temp.add(nickname);
        temp.add(startDate);
        temp.add(endDate);
        temp.add(kilometers);

        return service.createTemplate(temp);
    }
}
