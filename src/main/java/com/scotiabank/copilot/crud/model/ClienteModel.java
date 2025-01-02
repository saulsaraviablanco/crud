package com.scotiabank.copilot.crud.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "Cliente")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
}
