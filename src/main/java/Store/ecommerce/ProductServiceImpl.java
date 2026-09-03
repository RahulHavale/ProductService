package Store.ecommerce;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ModelMapper mapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        ProductEntity entity = mapper.map(request, ProductEntity.class);

        entity.setCreatedDate(LocalDate.now());
        entity.setUpdatedDate(LocalDate.now());

        repository.save(entity);

        return mapper.map(entity, ProductResponse.class);
    }

    @Override
    @Cacheable(value = "products",key = "'allProducts'")
    public List<ProductResponse> getAllProducts() {

        Type listType = new TypeToken<List<ProductResponse>>(){}.getType();

        return mapper.map(repository.findAll(), listType);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProduct(Long id) {

        ProductEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        return mapper.map(entity, ProductResponse.class);
    }

    @Override
    @CachePut(value = "products", key = "#id")
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        ProductEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setCategory(request.getCategory());
        entity.setPrice(request.getPrice());
        entity.setStock(request.getStock());
        entity.setUpdatedDate(LocalDate.now());

        repository.save(entity);

        return mapper.map(entity, ProductResponse.class);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {

        ProductEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id : " + id));

        repository.delete(entity);
    }
}
// to check the push command