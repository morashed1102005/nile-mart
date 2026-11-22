package com.route.nile_mart.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewCartForm {
    private String fullName;
    private String phone;
    private String email;
}