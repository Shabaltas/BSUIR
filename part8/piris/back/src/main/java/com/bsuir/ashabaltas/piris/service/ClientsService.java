package com.bsuir.ashabaltas.piris.service;

import com.bsuir.ashabaltas.piris.model.client.Client;
import com.bsuir.ashabaltas.piris.model.client.ClientDto;
import com.bsuir.ashabaltas.piris.model.client.ClientRepository;
import com.bsuir.ashabaltas.piris.model.client.ClientsPagedResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {

    private ClientRepository clientRepository;

    public ClientsService(@Autowired ClientRepository repository) {
        this.clientRepository = repository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public ClientsPagedResponse getPagedClients(int pageNumber, int pageSize) {
        return getPagedClients(PageRequest.of(pageNumber, pageSize));
    }

    public ClientsPagedResponse getPagedSortedClients(int pageNumber, int pageSize, String sortDir, String... sortedBy) {
        return getPagedClients(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortedBy)));
    }

    public List<Client> getSortedClients(String sortDir, String... sortedBy) {
        return clientRepository.findAll(Sort.by(Sort.Direction.fromString(sortDir), sortedBy));
    }

    public void createClient(ClientDto clientDto) {
        clientRepository.saveAndFlush(getClientFromDto(clientDto));
    }

    public Client getClient(Long clientId)
    {
         return clientRepository.getOne(clientId);
    }

    public void updateClient(long clientId, ClientDto clientDto) {
        Client client = getClientFromDto(clientDto);
        client.setId(clientId);
        clientRepository.saveAndFlush(client);
    }

    public void deleteClient(long clientId) {
        clientRepository.deleteById(clientId);
    }

    private ClientsPagedResponse getPagedClients(Pageable pageable) {
        Page<Client> page = clientRepository.findAll(pageable);
        return new ClientsPagedResponse(page.getTotalPages(), page.getContent());
    }

    private Client getClientFromDto(ClientDto clientDto) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return client;
    }
}
