package com.yougotcrushed.casst.mappers;

import com.yougotcrushed.casst.dtos.OrderDto;
import com.yougotcrushed.casst.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
