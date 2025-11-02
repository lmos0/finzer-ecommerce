package br.com.lmos.finzerecommerce.controller;

import br.com.lmos.finzerecommerce.dto.CustomerRequestDTO;
import br.com.lmos.finzerecommerce.dto.CustomerResponseDTO;
import br.com.lmos.finzerecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private  final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO requestDTO) {
        log.info("Criando novo cliente com email: {}", requestDTO.getEmail());

        CustomerResponseDTO response = customerService.createCustomer(requestDTO);

        URI  location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{publicId}")
                .buildAndExpand(response.getPublicId())
                .toUri();

        log.info("Cliente criado com sucesso. URI: {}", location);

        return ResponseEntity.created(location).body(response);

    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        log.info("Buscando todos os clientes");

        List<CustomerResponseDTO> customers = customerService.getAllCustomers();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByPublicId(@PathVariable String publicId) {
        log.info("Buscando cliente por publicId: {}", publicId);

        CustomerResponseDTO customer = customerService.getCustomerByPublicId(publicId);

        return ResponseEntity.ok(customer);
    }
}

//TODO 404 está retornando 500