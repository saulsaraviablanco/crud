package com.scotiabank.copilot.crud.controller;

import com.scotiabank.copilot.crud.dto.ClienteDTO;
import com.scotiabank.copilot.crud.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCliente() {
        List<ClienteDTO> clientes = Arrays.asList(new ClienteDTO(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null));
        when(clienteService.getAllClientes()).thenReturn(clientes);

        ResponseEntity<List<ClienteDTO>> response = clienteController.getCliente();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientes, response.getBody());
    }

    @Test
    void testGetClienteById() {
        ClienteDTO cliente = new ClienteDTO(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        when(clienteService.getClienteById(1)).thenReturn(Optional.of(cliente));

        ResponseEntity<ClienteDTO> response = clienteController.getClienteById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void testAddCliente() {
        ClienteDTO cliente = new ClienteDTO(null, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        ClienteDTO createdCliente = new ClienteDTO(1, "John Doe", "123 Main St", "555-1234", "john@example.com", null);
        when(clienteService.saveCliente(cliente)).thenReturn(createdCliente);

        ClienteDTO response = clienteController.addCliente(cliente);
        assertEquals(createdCliente, response);
    }

    @Test
    void testDeleteCliente() {
        when(clienteService.deleteCliente(1)).thenReturn(true);

        ResponseEntity<Void> response = clienteController.deleteCliente(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdateCliente() {
        ClienteDTO cliente = new ClienteDTO(1,"John Doe", "123 Main St", "555-1234", "john@example.com", null);
        when(clienteService.updateCliente(cliente)).thenReturn(cliente);

        ResponseEntity<ClienteDTO> response = clienteController.updateCliente(1,cliente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }
}