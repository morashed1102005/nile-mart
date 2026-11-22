package com.route.nile_mart.controllerr;

import com.route.nile_mart.checkout.OrderRegistry;
import com.route.nile_mart.common.Check;
import com.route.nile_mart.common.NotFoundException;
import com.route.nile_mart.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRegistry orderRegistry;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderRegistry.findAll());
        model.addAttribute("revenue", orderRegistry.revenue());
        return "orders/list";
    }

    @GetMapping("/{orderNumber}")
    public String view(@PathVariable("orderNumber") String orderNumber, Model model) {
        Order order = Check.found(
                orderRegistry.findByNumber(orderNumber),
                () -> new NotFoundException("Order " + orderNumber + " was not found")
        );
        model.addAttribute("order", order);
        return "orders/confirmation";
    }
}