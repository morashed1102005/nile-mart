package com.route.nile_mart.cart;

import com.route.nile_mart.common.Check;
import com.route.nile_mart.common.NotFoundException;
import com.route.nile_mart.common.StoreException;
import com.route.nile_mart.model.Customer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CartRegistry {

    private final ObjectProvider<ShoppingCart> cartProvider;
    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, ShoppingCart> carts = new ConcurrentHashMap<>();

    public CartRegistry(ObjectProvider<ShoppingCart> cartProvider) {
        this.cartProvider = cartProvider;
    }

    public ShoppingCart open(Customer customer) {
        ShoppingCart cart = cartProvider.getObject();
        int number = counter.incrementAndGet();
        cart.open(number, customer);
        carts.put(number, cart);
        return cart;
    }

    public ShoppingCart get(int number) {
        return Check.found(
                java.util.Optional.ofNullable(carts.get(number)),
                () -> new NotFoundException("not found")
        );
    }

    public List<CartView> views() {
        return carts.values().stream()
                .map(ShoppingCart::view)
                .sorted(Comparator.comparingInt(CartView::number))
                .toList();
    }

    public String addItem(Integer cartNumber, String sku, Integer quantity) {
        if (cartNumber == null) {
            throw new StoreException("Choose a cart");
        }
        if (sku == null || sku.isBlank()) {
            throw new StoreException("Choose a product");
        }
        if (quantity == null) {
            throw new StoreException("Enter a quantity");
        }

        ShoppingCart cart = get(cartNumber);
        cart.addItem(sku, quantity);
        return cart.view().lines().stream()
                .filter(line -> line.sku().equals(sku))
                .findFirst()
                .map(LineView::name)
                .orElse(sku);
    }
}