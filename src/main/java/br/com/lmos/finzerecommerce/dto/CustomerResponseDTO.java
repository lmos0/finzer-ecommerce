package br.com.lmos.finzerecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {

    private UUID publicId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
