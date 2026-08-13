package Store.ecommerce;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ProductResponse implements Serializable {
    private long productId;

    private String name;

    private String description;

    private int price;

    private int stock;

    private String category;

    private LocalDate createdDate;

    private LocalDate updatedDate;
}
