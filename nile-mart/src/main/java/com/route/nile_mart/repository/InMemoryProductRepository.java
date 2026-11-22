package com.route.nile_mart.repository;
import com.route.nile_mart.model.Category;
import com.route.nile_mart.model.Product;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProductRepository implements ProductRepository{

    private final Map<String,Product> products = new ConcurrentHashMap<>();

    @PostConstruct
    void init(){

        save(Product.builder()
                .sku("PH-X5-128")
                .name("Smartphone X5 128GB")
                .category(Category.ELECTRONICS)
                .price(new BigDecimal("16000.00"))
                .stockQuantity(12)
                .build());

        save(Product.builder()
                .sku("ACC-CHG-20W")
                .name("Fast Charger 20W")
                .category(Category.ELECTRONICS)
                .price(new BigDecimal("650.00"))
                .stockQuantity(40)
                .build());

        save(Product.builder()
                .sku("ACC-BUDS-6")
                .name("Wireless Earbuds")
                .category(Category.ELECTRONICS)
                .price(new BigDecimal("1100.00"))
                .stockQuantity(8)
                .build());

        save(Product.builder()
                .sku("FSH-GAL-001")
                .name("Cotton Galabeya")
                .category(Category.FASHION)
                .price(new BigDecimal("900.00"))
                .stockQuantity(25)
                .build());

        save(Product.builder()
                .sku("HOM-PAN-28")
                .name("Non-stick Frying Pan 28cm")
                .category(Category.HOME_KITCHEN)
                .price(new BigDecimal("1200.00"))
                .stockQuantity(3)
                .build());

        save(Product.builder()
                .sku("HOM-SPC-RCK")
                .name("Wooden Spice Rack")
                .category(Category.HOME_KITCHEN)
                .price(new BigDecimal("750.00"))
                .stockQuantity(10)
                .build());

        save(Product.builder()
                .sku("BTY-SUN-50")
                .name("Sun Cream SPF50")
                .category(Category.BEAUTY)
                .price(new BigDecimal("350.00"))
                .stockQuantity(0)
                .build());

        save(Product.builder()
                .sku("BK-CAI-TRI")
                .name("The Cairo Trilogy")
                .category(Category.BOOKS)
                .price(new BigDecimal("450.00"))
                .stockQuantity(15)
                .build());

    }

    @PreDestroy
    public void closeCatalog() {
        System.out.println("Catalog closed.");
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    @Override
    public Optional<Product> findById(String s) {
        return Optional.ofNullable(products.get(s));
    }

    @Override
    public Product save(Product entity) {
        products.put(entity.getSku(),entity);
        return entity;
    }

}
