package com.route.nile_mart.checkout;

import com.route.nile_mart.cart.CartView;
import com.route.nile_mart.cart.LineView;
import com.route.nile_mart.cart.ShoppingCart;
import com.route.nile_mart.common.Money;
import com.route.nile_mart.common.OutOfStockException;
import com.route.nile_mart.common.StoreException;
import com.route.nile_mart.model.Governorate;
import com.route.nile_mart.model.Order;
import com.route.nile_mart.model.OrderLine;
import com.route.nile_mart.model.Product;
import com.route.nile_mart.notification.NotificationSender;
import com.route.nile_mart.service.ProductService;
import com.route.nile_mart.shipping.ShippingQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductService productService;
    private final ShippingQuoteService shippingQuoteService;
    private final TaxCalculator taxCalculator;
    private final OrderRegistry orderRegistry;
    private final List<NotificationSender> notificationSenders;

    public Quote quote(ShoppingCart cart, Governorate governorate) {
        if (governorate == null) {
            return null;
        }
        if (cart.isEmpty()) {
            throw new StoreException("Cannot quote an empty cart.");
        }

        BigDecimal subtotal = cart.subtotal();
        BigDecimal vat = taxCalculator.calculate(subtotal);
        BigDecimal shippingFee = shippingQuoteService.quote(governorate, subtotal);
        BigDecimal total = Money.scale(subtotal.add(vat).add(shippingFee));

        return new Quote(subtotal, vat, shippingFee, total);
    }

    public synchronized Order placeOrder(ShoppingCart cart, Governorate governorate) {
        if (governorate == null) {
            throw new StoreException("Choose a governorate");
        }

        if (cart.isEmpty()) {
            throw new StoreException("Cannot place an order with an empty cart");
        }

        CartView snapshot = cart.view();

        for (LineView line : snapshot.lines()) {
            Product product = productService.findBySku(line.sku());
            if (product.getStockQuantity() == 0) {
                throw new OutOfStockException(product.getName() + " is out of stock");
            }
            if (line.quantity() > product.getStockQuantity()) {
                throw new OutOfStockException(
                        product.getStockQuantity() + " available for " + product.getName());
            }
        }

        Quote quote = quote(cart, governorate);

        for (LineView line : snapshot.lines()) {
            productService.reduceStock(line.sku(), line.quantity());
        }

        List<OrderLine> orderLines = snapshot.lines().stream()
                .map(l -> new OrderLine(l.sku(), l.name(), l.quantity(), l.linePrice()))
                .toList();

        String orderNumber = orderRegistry.nextNumber();
        Order order = new Order(
                orderNumber,
                snapshot.customer(),
                governorate,
                orderLines,
                quote.subtotal(),
                quote.vat(),
                quote.shippingFee(),
                quote.total()
        );
        orderRegistry.save(order);

        String message =orderNumber + " confirmed + Total " + Money.format(order.total());
        for (NotificationSender sender : notificationSenders) {
            sender.send(order.customer(), message);
        }
        cart.clear();
        return order;
    }
}
