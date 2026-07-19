package Store.ecommerce;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "product_table" )
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    private String description;

    private int price;

    private int stock;

    private String category;

    private LocalDate createdDate;

    private LocalDate updateDate;
}
