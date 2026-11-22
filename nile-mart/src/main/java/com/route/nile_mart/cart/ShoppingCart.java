package com.route.nile_mart.cart;

import com.route.nile_mart.common.Money;
import com.route.nile_mart.common.OutOfStockException;
import com.route.nile_mart.common.StoreException;
import com.route.nile_mart.model.Customer;
import com.route.nile_mart.model.Product;
import com.route.nile_mart.service.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Scope("prototype")
public class ShoppingCart {

        private final ProductService productService;
        private final int maxQuantityPerProduct;

        private final Map<String, Integer> lines = new LinkedHashMap<>();

        @Getter
        private int number;

        @Getter
        private Customer customer;

        private boolean opened = false;

        public ShoppingCart(
                ProductService productService,
                @Value("${store.max-quantity-per-product}") int maxQuantityPerProduct) {
            this.productService = productService;
            this.maxQuantityPerProduct = maxQuantityPerProduct;
        }

        public void open(int number, Customer customer) {
            if (opened) {
                throw new IllegalStateException("This cart has already opened");
            }
            this.number = number;
            this.customer = customer;
            this.opened = true;
        }

        public synchronized void addItem(String sku, int quantity) {
            if (quantity < 1) {
                throw new StoreException("Quantity must be at least 1");
            }

            Product product = productService.findBySku(sku);

            if (product.getStockQuantity() == 0) {
                throw new OutOfStockException(product.getName() + " is out of stock");
            }

            int newQty = lines.getOrDefault(sku, 0) + quantity;

            if (newQty > maxQuantityPerProduct) {
                throw new StoreException("You can add at most " + maxQuantityPerProduct + " of " + product.getName());
            }

            if (newQty > product.getStockQuantity()) {
                throw new OutOfStockException(product.getStockQuantity() + " available for " + product.getName());
            }

            lines.put(sku, newQty);
        }

        public BigDecimal subtotal() {
            return lines.entrySet().stream()
                    .map(entry -> productService.getPrice(entry.getKey(), entry.getValue()))
                    .reduce(Money.ZERO, BigDecimal::add);
        }

        public boolean isEmpty() {
            return lines.isEmpty();
        }

        public void clear() {
            lines.clear();
        }

        public Map<String, Integer> lines() {
            return Collections.unmodifiableMap(lines);
        }

        public CartView view() {
            List<LineView> lineViews = new ArrayList<>();
            int itemCount = 0;

            for (Map.Entry<String, Integer> entry : lines.entrySet()) {
                String sku = entry.getKey();
                int qty = entry.getValue();
                Product product = productService.findBySku(sku);
                BigDecimal linePrice = productService.getPrice(sku, qty);

                lineViews.add(new LineView(sku, product.getName(), qty, linePrice));
                itemCount += qty;
            }

            return new CartView(number, customer, lineViews, itemCount, subtotal(), isEmpty());
        }
}
