package br.com.lmos.finzerecommerce.service;

import br.com.lmos.finzerecommerce.entity.PhotoProduct;
import br.com.lmos.finzerecommerce.entity.Product;
import br.com.lmos.finzerecommerce.repository.PhotoProductRepository;
import br.com.lmos.finzerecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoProductService {

    private final PhotoProductRepository photoProductRepository;
    private final ProductRepository productRepository;
    private final S3Service s3Service;

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/jpg", "image/png",  "image/webp");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; //10mb


    public PhotoProduct uploadPhoto(UUID productPublicId, MultipartFile file, boolean isPrimary, String altText) {
        log.info("Uploading file {}", file.getOriginalFilename());
        log.info("Uploading photo to product {}", productPublicId);

        validateFile(file);

        Product product = productRepository.findByPublicId(productPublicId).orElseThrow(() -> new RuntimeException("Product not found"));

        String url = s3Service.uploadFile(file, "products");

        PhotoProduct photo = new PhotoProduct();
        photo.setProduct(product);
        photo.setUrl(url);
        photo.setAltText(altText != null ? altText : "");
        photo.setFileName(file.getOriginalFilename());
        photo.setContentType(file.getContentType());
        photo.setFileSize(file.getSize());
        photo.setMainPhoto(true); // conferir se isso está certo


        Long photoCount; // implementar repositório

        PhotoProduct savePhoto = photoProductRepository.save(photo);

        log.info("Photo saved. ID: {}", savePhoto.getPublicId());

        return savePhoto;

    }


    private void validateFile(MultipartFile file) {
        log.info("Validating file {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid file type");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File is too large. Max file size is " + MAX_FILE_SIZE);
        }
    }
}
