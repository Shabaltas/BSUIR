package com.bsuir.ashabaltas.piris.service;

import com.bsuir.ashabaltas.piris.model.client.Client;
import com.bsuir.ashabaltas.piris.model.client.ClientDto;
import com.bsuir.ashabaltas.piris.model.client.ClientRepository;
import com.bsuir.ashabaltas.piris.model.client.ClientsPagedResponse;
import com.bsuir.ashabaltas.piris.model.others.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OthersService {

    private CitiesRepository citiesRepository;
    private MaritalStatusesRepository maritalStatusesRepository;
    private CitizenshipRepository citizenshipRepository;
    private DisabilityRepository disabilityRepository;

    public OthersService(@Autowired CitiesRepository citiesRepository,@Autowired MaritalStatusesRepository maritalStatusesRepository,
                         @Autowired  CitizenshipRepository citizenshipRepository, @Autowired DisabilityRepository disabilityRepository) {
        this.citiesRepository = citiesRepository;
        this.maritalStatusesRepository = maritalStatusesRepository;
        this.citizenshipRepository = citizenshipRepository;
        this.disabilityRepository = disabilityRepository;
    }

    public List<MaritalStatus> getMaritalStatuses() {
        return maritalStatusesRepository.findAll();
    }

    public List<City> getCities() {
        return citiesRepository.findAll();
    }

    public List<Citizenship> getCitizenship() {
        return citizenshipRepository.findAll();
    }

    public List<Disability> getDisabilities() {
        return disabilityRepository.findAll();
    }



}
