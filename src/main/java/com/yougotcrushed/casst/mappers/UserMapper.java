package com.yougotcrushed.casst.mappers;

import com.yougotcrushed.casst.dtos.LoginUserRequest;
import com.yougotcrushed.casst.dtos.RegisterUserRequest;
import com.yougotcrushed.casst.dtos.UpdateUserRequest;
import com.yougotcrushed.casst.dtos.UserDto;
import com.yougotcrushed.casst.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDot(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
