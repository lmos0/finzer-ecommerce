package br.com.lmos.finzerecommerce.controller;


import br.com.lmos.finzerecommerce.dto.AdminRequestDTO;
import br.com.lmos.finzerecommerce.dto.AdminResponseDTO;
import br.com.lmos.finzerecommerce.dto.CustomerResponseDTO;
import br.com.lmos.finzerecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminResponseDTO> createAdmin(@RequestBody AdminRequestDTO adminRequestDTO) {
        AdminResponseDTO response =adminService.createAdmin(adminRequestDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity <List<AdminResponseDTO>> getAllAdmins() {
        log.info("Buscando administradores cadastrados");
        List<AdminResponseDTO> responseDTO = adminService.listAllAdmins();
        return ResponseEntity.ok(responseDTO);
    }


}
