package com.route.nile_mart.notification;

import com.route.nile_mart.model.Customer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class EmailNotificationSender extends AbstractNotificationSender{

    @Override
    public boolean supports(Customer customer) {
        return customer.email() != null;
    }

    @Override
    protected String channel() {
        return "Email";
    }

    @Override
    protected String address(Customer customer) {
        return customer.email();
    }
}
