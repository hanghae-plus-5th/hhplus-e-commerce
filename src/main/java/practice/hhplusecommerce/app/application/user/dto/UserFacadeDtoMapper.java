package practice.hhplusecommerce.app.application.user.dto;

import practice.hhplusecommerce.app.domain.user.User;

public class UserFacadeDtoMapper {

    public static UserFacadeDto toUserFacadeDto(User user) {
        return new UserFacadeDto(user.getId(), user.getName(), user.getAmount());
    }
}
