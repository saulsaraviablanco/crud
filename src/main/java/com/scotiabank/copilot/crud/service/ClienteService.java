package com.scotiabank.copilot.crud.service;

import com.scotiabank.copilot.crud.dto.ClienteDTO;
import com.scotiabank.copilot.crud.model.ClienteModel;
import com.scotiabank.copilot.crud.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClienteService(ClienteRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClienteDTO> getAllClientes() {
        List<ClienteModel> clientes = clientRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream().
                map(cliente -> modelMapper.map(cliente, ClienteDTO.class)).toList();
        return clienteDTOS;
    }

    public ClienteDTO getClienteById(Integer id) {
        ClienteModel clienteModel = clientRepository.findById(id).orElse(null);
        if (clienteModel == null) {
            return new ClienteDTO();
        }
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }

    public ClienteDTO addCliente(ClienteDTO clienteDTO) {
        ClienteModel clienteModel = modelMapper.map(clienteDTO, ClienteModel.class);
        clienteModel = clientRepository.save(clienteModel);
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }

    public boolean deleteCliente(Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        ClienteModel clienteModel = modelMapper.map(clienteDTO, ClienteModel.class);
        clienteModel = clientRepository.save(clienteModel);
        return modelMapper.map(clienteModel, ClienteDTO.class);
    }
}
