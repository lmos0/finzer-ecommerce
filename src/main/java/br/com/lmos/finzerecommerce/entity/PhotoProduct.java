package br.com.lmos.finzerecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PHOTO_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Column(nullable = false, length = 500)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 300)
    private String altText;

    @Column(nullable = false)
    private Boolean mainPhoto;

    private Integer displayOrder;

    @Column(length = 100)
    private String fileName;

    @Column(length = 50)
    private String contentType;

    private Long fileSize;

    private Integer width;

    private Integer height;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }

        if (this.mainPhoto == null) {
            this.mainPhoto = false;
        }

        if (this.displayOrder == null) {
            this.displayOrder = 0;
        }
        this.createdAt = LocalDateTime.now();
    }


    public boolean isImage(){
        return contentType != null && contentType.startsWith("image/");
    }

    public String getFormattedFileSize(){
        if (fileSize == null){
            return fileSize + " B";
        } else if (fileSize < 1024 * 1024) {
            return String.format("%.2f KB", fileSize / 1024.0);

        } else {
            return String.format("%.2f MB", fileSize / 1024.0);
        }

    }

    public String getFormattedDimensions() {
        if (width != null && height != null){
            return width + "x" + height + "px";
        }
        return "N/A";
    }

}
