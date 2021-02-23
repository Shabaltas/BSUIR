package com.bsuir.ashabaltas.piris.controller;

import com.bsuir.ashabaltas.piris.model.others.Citizenship;
import com.bsuir.ashabaltas.piris.model.others.City;
import com.bsuir.ashabaltas.piris.model.others.Disability;
import com.bsuir.ashabaltas.piris.model.others.MaritalStatus;
import com.bsuir.ashabaltas.piris.service.OthersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OthersController {

    private OthersService othersService;

    public OthersController(@Autowired OthersService othersService) {
        this.othersService = othersService;
    }

    @GetMapping("/marital_statuses")
    public List<MaritalStatus> getMaritalStatuses() {
        return othersService.getMaritalStatuses();
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return othersService.getCities();
    }

    @GetMapping("/citizenship")
    public List<Citizenship> getCitizenship() {
        return othersService.getCitizenship();
    }

    @GetMapping("/disabilities")
    public List<Disability> getDisabilities() {
        return othersService.getDisabilities();
    }
}
