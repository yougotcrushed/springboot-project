package com.yougotcrushed.casst.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    @JsonProperty("product_id")
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Byte categoryId;

}
