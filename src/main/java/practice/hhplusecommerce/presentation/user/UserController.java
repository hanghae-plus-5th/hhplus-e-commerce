package practice.hhplusecommerce.presentation.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.presentation.user.dto.response.UserResponseDto;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @GetMapping("/{user-id}/amount")
  public UserResponseDto.UserResponse getUserAmount(
      @PathVariable("user-id") String userId
  ) {
    return new UserResponseDto.UserResponse(1L, "백현명", 5000);
  }

  @PatchMapping("/{user-id}/amount")
  public UserResponseDto.UserResponse patchUserAmount(
      @PathVariable("user-id") String userId
  ) {
    return new UserResponseDto.UserResponse(1L, "백현명", 5000);
  }
}
