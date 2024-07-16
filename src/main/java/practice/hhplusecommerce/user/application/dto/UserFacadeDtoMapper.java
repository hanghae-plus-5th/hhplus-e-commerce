package practice.hhplusecommerce.user.application.dto;

import practice.hhplusecommerce.user.business.entity.User;

public class UserFacadeDtoMapper {

    public static UserFacadeDto toUserFacadeDto(User user) {
        return new UserFacadeDto(user.getId(), user.getName(), user.getAmount());
    }
}
