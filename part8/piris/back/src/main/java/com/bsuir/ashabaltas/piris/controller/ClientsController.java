package com.bsuir.ashabaltas.piris.controller;

import com.bsuir.ashabaltas.piris.model.client.Client;
import com.bsuir.ashabaltas.piris.model.client.ClientDto;
import com.bsuir.ashabaltas.piris.model.client.ClientsPagedResponse;
import com.bsuir.ashabaltas.piris.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping(path = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsController {

    private ClientsService clientsService;
    @Autowired
    public ClientsController(ClientsService service) {
        this.clientsService = service;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientsService.getClients();
    }

    @GetMapping(params = {"page_num", "page_size"})
    public ClientsPagedResponse getClients(@RequestParam("page_num") int pageNumber, @RequestParam("page_size") int pageSize) {
        return clientsService.getPagedClients(pageNumber, pageSize);
    }

    @GetMapping(params = {"sorted_by", "sort_dir"})
    public List<Client> getSortedClients(@RequestParam("sorted_by") String sortedBy, @RequestParam("sort_dir") String sortDir) {
        if (sortedBy.equalsIgnoreCase("surname&name"))
            return clientsService.getSortedClients(sortDir, "surname", "name");
        return clientsService.getSortedClients(sortDir, sortedBy);
        //todo exception
    }

    @GetMapping(params = {"page_num", "page_size", "sorted_by", "sort_dir"})
    public ClientsPagedResponse getSortedClients(@RequestParam("page_num") int pageNumber, @RequestParam("page_size") int pageSize,
                                                 @RequestParam("sorted_by") String sortedBy, @RequestParam("sort_dir") String sortDir) {
        //todo create returned object with total pages count
        if (sortedBy.equalsIgnoreCase("surname&name"))
            return clientsService.getPagedSortedClients(pageNumber, pageSize, sortDir, "surname", "name");
        return clientsService.getPagedSortedClients(pageNumber, pageSize, sortDir, sortedBy);
       //todo exception
    }

    @GetMapping("/{clientId}")
    public Client getClient(@PathVariable Long clientId) {
        return clientsService.getClient(clientId);
    }

    @PostMapping
    public void createClient(@Valid @RequestBody ClientDto client) {
        clientsService.createClient(client);
    }

    @PutMapping("/{clientId}")
    public void updateClient(@Valid @RequestBody ClientDto client, @PathVariable Long clientId) {
        clientsService.updateClient(clientId, client);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientsService.deleteClient(clientId);
    }
}
