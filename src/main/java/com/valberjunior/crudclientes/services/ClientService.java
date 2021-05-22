package com.valberjunior.crudclientes.services;

import com.valberjunior.crudclientes.dto.ClientDTO;
import com.valberjunior.crudclientes.entities.Client;
import com.valberjunior.crudclientes.repositories.ClientRepository;
import com.valberjunior.crudclientes.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Page<ClientDTO> findAll(PageRequest pageRequest) {
        Page<Client> all = repository.findAll(pageRequest);
        return all.map(ClientDTO::new);
    }

    public ClientDTO findById(Long id) {
        Optional<Client> clientOptional = repository.findById(id);
        return clientOptional.map(ClientDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
    }

    @Transactional
    public ClientDTO create(ClientDTO dto) {
        var client = new Client(dto);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        var client = new Client(this.findById(id));
        dto.setId(client.getId());
        client = new Client(dto);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}
