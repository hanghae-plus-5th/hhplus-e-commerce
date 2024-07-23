package practice.hhplusecommerce.user.presentation;

import static practice.hhplusecommerce.iterceptor.JwtTokenInterceptor.TOKEN_INFO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.common.jwt.TokenInfoDto;
import practice.hhplusecommerce.user.application.UserFacade;
import practice.hhplusecommerce.user.presentation.dto.UserRequestDto;
import practice.hhplusecommerce.user.presentation.dto.UserResponseDto;
import practice.hhplusecommerce.user.presentation.dto.UserResponseDto.UserResponse;
import practice.hhplusecommerce.user.presentation.dto.UserResponseDtoMapper;

@Tag(name = "유저")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserFacade userFacade;

  @Operation(summary = "유저 로그인")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @PostMapping("/login")
  public UserResponseDto.TokenUserResponse login(
      @RequestBody UserRequestDto.Request request
  ) {
    return UserResponseDtoMapper.toTokenUserResponse(userFacade.login(request.getName()));
  }

  @Operation(summary = "유저 회원가입")
  @PostMapping("/join")
  public void join(
      @RequestBody UserRequestDto.Request request
  ) {
    userFacade.join(request.getName());
  }

  @Operation(summary = "유저 잔액 조회")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @GetMapping("/amount")
  public UserResponse getUserAmount(
      @RequestAttribute(value = TOKEN_INFO) TokenInfoDto tokenInfoDto
  ) {
    return UserResponseDtoMapper.toUserResponse(userFacade.getUserAmount(tokenInfoDto.getUserId()));
  }

  @Operation(summary = "유저 잔액 충전")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @PostMapping("/amount")
  public UserResponse patchUserAmount(
      @RequestAttribute(value = TOKEN_INFO) TokenInfoDto tokenInfoDto,
      @RequestParam("amount") Integer amount
  ) {
    return UserResponseDtoMapper.toUserResponse(userFacade.chargeUserAmount(tokenInfoDto.getUserId(), amount));
  }
}
