package br.com.lmos.finzerecommerce.service;

import br.com.lmos.finzerecommerce.dto.CustomerRequestDTO;
import br.com.lmos.finzerecommerce.dto.CustomerResponseDTO;
import br.com.lmos.finzerecommerce.entity.Customer;
import br.com.lmos.finzerecommerce.exceptions.InvalidDataException;
import br.com.lmos.finzerecommerce.exceptions.ResourceAlreadyExistsException;
import br.com.lmos.finzerecommerce.exceptions.ResourceNotFoundException;
import br.com.lmos.finzerecommerce.repository.CustomerRepository;
import br.com.lmos.finzerecommerce.util.CpfValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
        log.info("Criando novo cliente com email: {}", requestDTO.getEmail());

        if (customerRepository.findByEmail(requestDTO.getEmail().toLowerCase()).isPresent()) {
            throw new ResourceAlreadyExistsException("Cliente", "email", requestDTO.getEmail());
        }

        String cleanCpf = requestDTO.getCpf().replaceAll("\\D", "");
        if (customerRepository.existsByCpf(cleanCpf)) {
            throw new ResourceAlreadyExistsException("Cliente", "CPF", requestDTO.getCpf());
        }

        validateCustomerCpf(requestDTO);

        Customer customer = new Customer();
        customer.setPublicId(UUID.randomUUID());
        customer.setFirstName(requestDTO.getFirstName().trim());
        customer.setLastName(requestDTO.getLastName().trim());
        customer.setEmail(requestDTO.getEmail().toLowerCase().trim());
        customer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        customer.setCpf(cleanCpf);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        log.info("Cliente criado com sucesso: {}", savedCustomer);

        return mapToResponseDTO(savedCustomer);

    }

    public List<CustomerResponseDTO> getAllCustomers() {
        log.debug("Buscando todos os clientes");

        return customerRepository.findAll().stream().map(this::mapToResponseDTO).toList();
    }

    public CustomerResponseDTO getCustomerByPublicId(String publicIdStr) {
        UUID publicId;
        try {
            publicId = UUID.fromString(publicIdStr);
        } catch (IllegalArgumentException e){
            throw  new InvalidDataException("Formato de UUID inválido: " + publicIdStr);
        }
        log.debug("Buscando cliente por publicId: {}", publicId);

        Customer customer = customerRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "publicId", publicId));

        return mapToResponseDTO(customer);

    }


    private void validateCustomerCpf(CustomerRequestDTO requestDTO) {

        String cpf = requestDTO.getCpf().replaceAll("\\D", "");
        if (cpf.isBlank()) {
            throw new InvalidDataException("CPF é obrigatório");
        }
        if (!CpfValidator.isValid(cpf)) {
            throw new InvalidDataException("CPF inválido:" + requestDTO.getCpf());
        }
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setPublicId(customer.getPublicId());
        responseDTO.setFirstName(customer.getFirstName());
        responseDTO.setLastName(customer.getLastName());
        responseDTO.setEmail(customer.getEmail());
        responseDTO.setPhone(customer.getPhone());
        responseDTO.setCpf(customer.getCpf());
        responseDTO.setCreatedAt(customer.getCreatedAt());
        responseDTO.setUpdatedAt(customer.getUpdatedAt());
        return responseDTO;
    }

    private void validateCustomerData(CustomerRequestDTO requestDTO) {
        if (requestDTO.getFirstName() == null || requestDTO.getFirstName().trim().isEmpty()) {
            throw new InvalidDataException("Nome é obrigatório");
        }
        if (requestDTO.getLastName() == null || requestDTO.getLastName().trim().isEmpty()) {
            throw new InvalidDataException("Sobrenome é obrigatório");
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("Email é obrigatório");
        }
        if (requestDTO.getPassword() == null || requestDTO.getPassword().trim().isEmpty()) {
            throw new InvalidDataException("Senha é obrigatória");
        }
        // TODO usar outra validação de email
        if (!requestDTO.getEmail().contains("@")) {
            throw new InvalidDataException("Email inválido");
        }
    }
}