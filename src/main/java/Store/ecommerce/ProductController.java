package Store.ecommerce;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping("api/v1/createProduct")
    public String createProduct(
            @RequestBody ProductRequest request
    ){
        productService.createProduct(request);
        return "Created";
    }

    @PutMapping("api/v1/updateProduct/{id}")
    public String updateProduct(@PathVariable long id,
            @RequestBody ProductRequest request
    ){
        productService.updateProduct(id,request);
        return "Updated";
    }

    @GetMapping("/api/v1/all")
    public List<ProductResponse> listOfProducts(){
        List<ProductResponse> datas = productService.listOfProducts();
        return datas;
    }

    @GetMapping("/api/v1/search")
    public ProductResponse searchById(@RequestParam long id){
        ProductResponse response = productService.searchById(id);
        return response;
    }

    @DeleteMapping("/api/delete")
    public String deleteProdById(@RequestParam long id){
        productService.deleteProdById(id);
        return "Deleted Succesfully";
    }
}
