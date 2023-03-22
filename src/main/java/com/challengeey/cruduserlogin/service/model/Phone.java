package com.challengeey.cruduserlogin.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    private String number;
    private String cityCode;
    private String countryCode;
}