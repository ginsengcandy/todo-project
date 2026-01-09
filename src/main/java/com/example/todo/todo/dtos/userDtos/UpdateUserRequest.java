package com.example.todo.todo.dtos.userDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Size(min = 4, message = "4글자 이상 입력하여 주십시오.")
    @NotBlank(message = "필수 입력 항목입니다.")
    private String username;
    @Email
    @NotBlank(message = "필수 입력 항목입니다.")
    private String email;
    @Size(min = 8, max = 15, message = "비밀번호는 8-15자여야 합니다")
    @Pattern(regexp = "^\\S*$", message = "공백은 허용되지 않습니다")
    @Pattern(regexp = ".*[0-9].*", message = "숫자를 포함해야 합니다")
    @Pattern(regexp = ".*[!@#$%^&*].*", message = "특수문자를 포함해야 합니다")
    @NotBlank(message = "필수 입력 항목입니다.")
    private String password;
}
