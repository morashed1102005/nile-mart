package com.route.nile_mart.controllerr;

import com.route.nile_mart.cart.CartRegistry;
import com.route.nile_mart.cart.ShoppingCart;
import com.route.nile_mart.checkout.CheckoutService;
import com.route.nile_mart.model.Governorate;
import com.route.nile_mart.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carts/{number}/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CartRegistry cartRegistry;
    private final CheckoutService checkoutService;

    @GetMapping
    public String form(
            @PathVariable("number") int number,
            @RequestParam(name = "governorate", required = false) Governorate governorate,
            Model model) {
        ShoppingCart cart = cartRegistry.get(number);
        model.addAttribute("cart", cart.view());
        model.addAttribute("governorates", Governorate.values());
        model.addAttribute("selected", governorate);
        model.addAttribute("quote", checkoutService.quote(cart, governorate));
        return "checkout/form";
    }

    @PostMapping
    public String place(
            @PathVariable("number") int number,
            @RequestParam(name = "governorate", required = false) Governorate governorate,
            RedirectAttributes redirectAttributes) {
        Order order = checkoutService.placeOrder(cartRegistry.get(number), governorate);
        redirectAttributes.addFlashAttribute("message", "Order " + order.orderNumber() + " confirmed.");
        return "redirect:/orders/" + order.orderNumber();
    }
}