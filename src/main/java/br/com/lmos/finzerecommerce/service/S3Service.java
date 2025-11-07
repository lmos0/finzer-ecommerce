package br.com.lmos.finzerecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;



    public String uploadFile(MultipartFile file, String folder) {

        try {

            String fileName = generateFileName(file.getOriginalFilename());
            String key = folder + "/" + fileName;

            PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));


            String url = String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
            log.info("Arquivo enviado com sucesso para S3: {}", url);
            return url;
        } catch (IOException e) {
            log.error("Erro ao fazer upload para S3", e);
            throw new RuntimeException("Erro ao fazer upload do arquivo: " + e.getMessage());

        }

    }
    private String generateFileName(String originalFileName){
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private String extractKeyFromUrl(String fileUrl) {
        String[] parts = fileUrl.split(".s3.amazonaws.com/");

        if (parts.length > 1) {
            return parts[1];
        }
        throw new IllegalArgumentException("Invalid file url: " + fileUrl);
    }
}
