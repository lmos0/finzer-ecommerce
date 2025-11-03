package br.com.lmos.finzerecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {

    private UUID publicId;
    private String name;
    private String email;

}
