package com.scotiabank.copilot.crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        return clienteService.getClienteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/agregarCliente")
    public ClienteDTO addCliente(@RequestBody ClienteDTO clienteDTO){
        return this.clienteService.saveCliente(clienteDTO);
    }

    @DeleteMapping("/eliminarCliente/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        boolean eliminado = this.clienteService.deleteCliente(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/actualizarCliente/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Integer id,@RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        ClienteDTO updatedCliente = null;
        updatedCliente = clienteService.updateCliente(clienteDTO);
        return new ResponseEntity<>(updatedCliente, HttpStatus.OK);

    }

}
