package com.scotiabank.copilot.crud.service;

import com.scotiabank.copilot.crud.dto.ClienteDTO;
import com.scotiabank.copilot.crud.model.ClienteModel;
import com.scotiabank.copilot.crud.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClienteDTO> getAllClientes() {
        List<ClienteModel> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO getClienteById(Integer id) {
        ClienteModel clienteModel = clienteRepository.findById(id).orElse(null);
        if (clienteModel == null) {
            return null;
        }
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }

    public ClienteDTO addCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("ClienteDTO cannot be null");
        }
        ClienteModel clienteModel = modelMapper.map(clienteDTO, ClienteModel.class);
        if (clienteModel == null) {
            throw new IllegalArgumentException("Mapping ClienteDTO to ClienteModel failed");
        }
        clienteModel = clienteRepository.save(clienteModel);
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }

    public boolean deleteCliente(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            throw new IllegalArgumentException("ClienteDTO cannot be null");
        }
        if (!clienteRepository.existsById(clienteDTO.getId())) {
            throw new IllegalArgumentException("Cliente with ID " + clienteDTO.getId() + " does not exist");
        }
        ClienteModel clienteModel = modelMapper.map(clienteDTO, ClienteModel.class);
        if (clienteModel == null) {
            throw new IllegalArgumentException("Mapping ClienteDTO to ClienteModel failed");
        }
        clienteModel = clienteRepository.save(clienteModel);
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }
}