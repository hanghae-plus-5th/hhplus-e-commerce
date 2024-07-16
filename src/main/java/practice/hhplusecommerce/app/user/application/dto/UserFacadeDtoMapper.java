package practice.hhplusecommerce.app.user.application.dto;

import practice.hhplusecommerce.app.user.business.entity.User;

public class UserFacadeDtoMapper {

    public static UserFacadeDto toUserFacadeDto(User user) {
        return new UserFacadeDto(user.getId(), user.getName(), user.getAmount());
    }
}
