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

    /*@GetMapping("/listarCliente/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id){
        return ResponseEntity.ok(this.clienteService.getClienteById(id));
    }*/

    @GetMapping("/listarCliente/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        ClienteDTO cliente = this.clienteService.getClienteById(id);

        if (cliente != null) {
            return ResponseEntity.ok(cliente); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping("/agregarCliente")
    public ResponseEntity<ClienteDTO> addCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteCreado = this.clienteService.addCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

    /*@GetMapping("/agregarCliente")
    public ResponseEntity<ClienteDTO> addCliente(ClienteDTO clienteDTO){
        return ResponseEntity.ok(this.clienteService.addCliente(clienteDTO));
    }*/

    /*@GetMapping("/eliminarCliente/{id}")
    public ResponseEntity<Boolean> deleteCliente(@PathVariable Integer id){
        return ResponseEntity.ok(this.clienteService.deleteCliente(id));
    }*/

    @DeleteMapping("/eliminarCliente/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        boolean eliminado = this.clienteService.deleteCliente(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /*@GetMapping("/actualizarCliente")
    public ResponseEntity<ClienteDTO> updateCliente(ClienteDTO clienteDTO){
        return ResponseEntity.ok(this.clienteService.updateCliente(clienteDTO));
    }*/

    @PutMapping("/actualizarCliente")
    public ResponseEntity<ClienteDTO> updateCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = this.clienteService.updateCliente(clienteDTO);

        if (clienteActualizado == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found si el cliente no existe
        }

        return ResponseEntity.ok(clienteActualizado); // 200 OK con el cliente actualizado
    }



}
