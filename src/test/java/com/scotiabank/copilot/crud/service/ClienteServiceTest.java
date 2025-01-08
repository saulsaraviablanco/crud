package com.scotiabank.copilot.crud.service;

import com.scotiabank.copilot.crud.dto.ClienteDTO;
import com.scotiabank.copilot.crud.model.ClienteModel;
import com.scotiabank.copilot.crud.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteService(clienteRepository, modelMapper);
    }

    @Test
    void testGetAllClientes() {
        List<ClienteModel> clientes = Arrays.asList(new ClienteModel(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null));
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteDTO> result = clienteService.getAllClientes();

        assertEquals(clientes.size(), result.size());
    }

    @Test
    void testGetClienteById() {
        ClienteModel cliente = new ClienteModel(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        ClienteDTO clienteDTO = new ClienteDTO(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<ClienteDTO> response = clienteService.getClienteById(1);

        assertTrue(response.isPresent(), "El cliente debe estar presente");
        assertEquals(clienteDTO, response.get(), "El cliente obtenido debe ser el mismo que el esperado");
    }


    @Test
    void testAddCliente() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        ClienteModel createdCliente = new ClienteModel(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(createdCliente);

        ClienteDTO result = clienteService.saveCliente(clienteDTO);

        assertEquals(createdCliente.getId(), result.getId());
    }

    @Test
    void testDeleteCliente() {
        when(clienteRepository.existsById(1)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1);

        boolean result = clienteService.deleteCliente(1);

        assertTrue(result);
        verify(clienteRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdateCliente() {
        ClienteDTO clienteDTO = new ClienteDTO(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        ClienteModel clienteModel = new ClienteModel(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);

        when(clienteService.getClienteById(clienteDTO.getId())).thenReturn(Optional.of(clienteDTO));

        when(clienteRepository.existsById(1)).thenReturn(true);
        when(clienteRepository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        ClienteDTO result = clienteService.updateCliente(clienteDTO); //ACA SE CAE

        assertEquals(clienteModel.getId(), result.getId());
    }
}