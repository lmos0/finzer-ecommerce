package br.com.lmos.finzerecommerce.service;

import br.com.lmos.finzerecommerce.dto.AdminRequestDTO;
import br.com.lmos.finzerecommerce.dto.AdminResponseDTO;
import br.com.lmos.finzerecommerce.entity.Admin;
import br.com.lmos.finzerecommerce.exceptions.InvalidDataException;
import br.com.lmos.finzerecommerce.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminResponseDTO createAdmin(AdminRequestDTO adminRequestDTO) {
        log.info("Criando novo administrador com email {}", adminRequestDTO.getEmail());

        validateAdminData(adminRequestDTO);

        Admin admin = new Admin();
        admin.setPublicId(UUID.randomUUID());
        admin.setName(adminRequestDTO.getFirstName());
        admin.setPassword(passwordEncoder.encode(adminRequestDTO.getPassword()));
        admin.setEmail(adminRequestDTO.getEmail());

        Admin savedAdmin = adminRepository.save(admin);
        log.info("Criado administrador com sucesso {}", savedAdmin);

        return mapToAdminResponseDTO(savedAdmin);
    }

    public List<AdminResponseDTO> listAllAdmins() {
        log.info("Listando todos os administradores");

        return adminRepository.findAll().stream().map(this::mapToAdminResponseDTO).toList();
    }

    private void validateAdminData(AdminRequestDTO adminRequestDTO)  {
        if (adminRequestDTO.getFirstName() == null || adminRequestDTO.getFirstName().isEmpty()) {
            throw new InvalidDataException("Nome é obrigatório");
        }
        if (adminRequestDTO.getPassword() == null || adminRequestDTO.getPassword().isEmpty()) {
            throw new InvalidDataException("Senha é obrigatória");
        }
        if (adminRequestDTO.getEmail() == null || adminRequestDTO.getEmail().isEmpty()) {
            throw new InvalidDataException("Email é obrigatório");
        }

    }

    private AdminResponseDTO mapToAdminResponseDTO(Admin admin) {
        AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setPublicId(admin.getPublicId());
        adminResponseDTO.setName(admin.getName());
        adminResponseDTO.setEmail(admin.getEmail());
        return adminResponseDTO;
    }
}
