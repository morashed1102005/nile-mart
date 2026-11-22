package com.route.nile_mart.notification;

import com.route.nile_mart.model.Customer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class SmsNotificationSender extends AbstractNotificationSender{
    @Override
    protected String channel() {
        return "SMS";
    }

    @Override
    protected String address(Customer customer) {
        return customer.phone();
    }
}
