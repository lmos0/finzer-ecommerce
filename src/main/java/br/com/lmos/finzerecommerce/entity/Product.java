package br.com.lmos.finzerecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Column(nullable = false, updatable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(precision = 3, scale = 2)
    private BigDecimal weight;

    @Column(precision = 3, scale = 2)
    private BigDecimal height;

    @Column(precision = 3, scale = 2)
    private BigDecimal depth;

    private Integer viewCount;

    //TODO Adicionar Carrinho
    //TODO Adicionar Categoria


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoProduct> photos = new ArrayList<>();

    private Boolean active;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        if (this.publicId == null) {
            this.publicId = UUID.randomUUID();
        }

        if (this.viewCount == null) {
            this.viewCount = 0;
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }


    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void addPhoto(PhotoProduct photo){
        photos.add(photo);
        photo.setProduct(this);
    }

    public void removePhoto(PhotoProduct photo){
        photos.remove(photo);
        photo.setProduct(null);
    }

    public void incrementViewCount(){
        this.viewCount++;
    }

    public boolean isInStock(){
        return this.stockQuantity > 0;
    }


}
