package com.scotiabank.copilot.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scotiabank.copilot.crud.service.ClienteService;
import com.scotiabank.copilot.crud.dto.ClienteDTO;

import java.util.List;

@RestController
@RequestMapping("/v1/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/listarClientes")
    public ResponseEntity<List<ClienteDTO>> getCliente(){
        return ResponseEntity.ok(this.clienteService.getAllClientes());
    }

    @GetMapping("/listarCliente/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id){
        return ResponseEntity.ok(this.clienteService.getClienteById(id));
    }

    @GetMapping("/agregarCliente")
    public ResponseEntity<ClienteDTO> addCliente(ClienteDTO clienteDTO){
        return ResponseEntity.ok(this.clienteService.addCliente(clienteDTO));
    }

    @GetMapping("/eliminarCliente/{id}")
    public ResponseEntity<Boolean> deleteCliente(@PathVariable Integer id){
        return ResponseEntity.ok(this.clienteService.deleteCliente(id));
    }

    @GetMapping("/actualizarCliente")
    public ResponseEntity<ClienteDTO> updateCliente(ClienteDTO clienteDTO){
        return ResponseEntity.ok(this.clienteService.updateCliente(clienteDTO));
    }



}
