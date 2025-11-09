package br.com.lmos.finzerecommerce.controller;

import br.com.lmos.finzerecommerce.service.PhotoProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/products/{productPublicId}/photos")
public class PhotoProductController {

    private final PhotoProductService photoProductService;




}
