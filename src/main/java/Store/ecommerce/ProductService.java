package Store.ecommerce;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo productRepo;

    private ModelMapper modelMapper;
    ProductService(ProductRepo productRepo, ModelMapper modelMapper){
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    public void createProduct(ProductRequest request){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());
        productEntity.setCategory(request.getCategory());
        productEntity.setPrice(request.getPrice());
        productEntity.setStock(request.getStock());
        productEntity.setDescription(request.getDescription());
        productEntity.setCreatedDate(LocalDate.now());

        productRepo.save(productEntity);
    }

    public void updateProduct(long id, ProductRequest request) {
        ProductEntity productEntity = productRepo.findById(id).orElse(null);

        if (productEntity == null) {
            return;
        }
        productEntity.setName(request.getName());
        productEntity.setCategory(request.getCategory());
        productEntity.setPrice(request.getPrice());
        productEntity.setStock(request.getStock());
        productEntity.setDescription(request.getDescription());
        productEntity.setUpdateDate(LocalDate.now());

        productRepo.save(productEntity);
    }

    public List<ProductResponse> listOfProducts(){
        List<ProductEntity> all = productRepo.findAll();
        Type listType = new TypeToken<List<ProductResponse>>() {}.getType();
        List<ProductResponse> datas = modelMapper.map(all, listType);
        return datas;
    }

    public ProductResponse searchById(long id) {
        Optional<ProductEntity> byId = productRepo.findById(id);
        ProductResponse map = modelMapper.map(byId, ProductResponse.class);
        return map;
    }

    public void deleteProdById(long id){
        productRepo.deleteById(id);
    }
}
