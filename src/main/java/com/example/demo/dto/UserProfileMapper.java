package com.example.demo.dto;

import com.example.demo.model.UserProfile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // важное: интеграция с Spring через @Component
public interface UserProfileMapper {

    UserProfileDto toDto(UserProfile entity);

    List<UserProfileDto> toDtoList(List<UserProfile> users);

}
