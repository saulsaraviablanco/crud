package com.scotiabank.copilot.crud.repository;

import com.scotiabank.copilot.crud.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteModel,Integer>{

}
