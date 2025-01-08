package com.scotiabank.copilot.crud.service;

import com.scotiabank.copilot.crud.dto.ClienteDTO;
import com.scotiabank.copilot.crud.model.ClienteModel;
import com.scotiabank.copilot.crud.repository.ClienteRepository;
import com.scotiabank.copilot.crud.exception.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<ClienteDTO> getClienteById(Integer id) {
        ClienteModel clienteModel = clienteRepository.findById(id).orElse(null);
        if (clienteModel == null) {
            return Optional.empty();
        }

        ClienteDTO clienteDTO = modelMapper.map(clienteModel, ClienteDTO.class);
        return Optional.of(clienteDTO);
    }

    public Optional<ClienteModel> getClienteById2(Integer id) {
        return clienteRepository.findById(id);
    }

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        List<ClienteModel> clientesConEmail = clienteRepository.findByEmail(clienteDTO.getEmail());
        if(clientesConEmail != null && !clientesConEmail.isEmpty()){
            throw new ConflictException("El cliente con ese email ya existe : " + clienteDTO.getEmail());
        }
        ClienteModel clienteModel = modelMapper.map(clienteDTO, ClienteModel.class);
        clienteModel = clienteRepository.save(clienteModel);

        return modelMapper.map(clienteModel,ClienteDTO.class);
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
        Optional<ClienteModel> clienteExistente = getClienteById2(clienteDTO.getId());
        if (!clienteExistente.isPresent()) {
            throw new ResourceNotFoundException("Cliente con ID " + clienteDTO.getId() + " no encontrado.");
        }

        List<ClienteModel> clientesConEmail = clienteRepository.findByEmail(clienteDTO.getEmail());
        if (clientesConEmail != null && !clientesConEmail.isEmpty()) {
            for (ClienteModel cliente : clientesConEmail) {
                if (!cliente.getId().equals(clienteDTO.getId())) {
                    throw new ConflictException("El cliente con ese email ya existe: " + clienteDTO.getEmail());
                }
            }
        }
        ClienteModel clienteModel = modelMapper.map(clienteDTO, ClienteModel.class);
        clienteModel = clienteRepository.save(clienteModel);
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }

}