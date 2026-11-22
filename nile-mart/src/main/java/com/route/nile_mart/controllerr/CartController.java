package com.route.nile_mart.controllerr;

import com.route.nile_mart.cart.CartRegistry;
import com.route.nile_mart.cart.ShoppingCart;
import com.route.nile_mart.controller.form.AddItemForm;
import com.route.nile_mart.controller.form.NewCartForm;
import com.route.nile_mart.model.Customer;
import com.route.nile_mart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartRegistry cartRegistry;
    private final ProductService productService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("carts", cartRegistry.views());
        return "carts/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new NewCartForm());
        return "carts/new";
    }

    @PostMapping
    public String create(@ModelAttribute("form") NewCartForm form, RedirectAttributes redirectAttributes) {
        Customer customer = new Customer(form.getFullName(), form.getPhone(), form.getEmail());
        ShoppingCart cart = cartRegistry.open(customer);
        redirectAttributes.addFlashAttribute("message", "Cart " + cart.getNumber() + " created for " + customer.fullName() + ".");
        return "redirect:/carts/" + cart.getNumber();
    }

    @GetMapping("/{number}")
    public String view(@PathVariable("number") int number, Model model) {
        model.addAttribute("cart", cartRegistry.get(number).view());
        model.addAttribute("products", productService.search(p -> p.getStockQuantity() > 0));
        return "carts/view";
    }

    @PostMapping("/items")
    public String addItem(@ModelAttribute AddItemForm form, RedirectAttributes redirectAttributes) {
        String productName = cartRegistry.addItem(form.getCartNumber(), form.getSku(), form.getQuantity());
        redirectAttributes.addFlashAttribute("message", "Added " + form.getQuantity() + " x " + productName + " to cart " + form.getCartNumber() + ".");
        return "redirect:/carts/" + form.getCartNumber();
    }
}