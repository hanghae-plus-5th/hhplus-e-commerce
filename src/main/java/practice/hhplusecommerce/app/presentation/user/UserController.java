package practice.hhplusecommerce.app.presentation.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.app.presentation.user.dto.response.UserResponseDto.UserResponse;

@Tag(name = "유저")
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Operation(summary = "유저 잔액 조회")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @GetMapping("/{user-id}/amount")
  public UserResponse getUserAmount(
      @PathVariable("user-id") String userId
  ) {
    return new UserResponse(1L, "백현명", 5000);
  }

  @Operation(summary = "유저 잔액 충전")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @PatchMapping("/{user-id}/amount")
  public UserResponse patchUserAmount(
      @PathVariable("user-id") String userId,
      @RequestParam("amount") Integer amount
  ) {
    return new UserResponse(1L, "백현명", 5000);
  }
}
