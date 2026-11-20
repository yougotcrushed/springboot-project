package com.yougotcrushed.casst.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class WebhookRequest {
    private Map<String,String> headers;
    private String payload;
}
