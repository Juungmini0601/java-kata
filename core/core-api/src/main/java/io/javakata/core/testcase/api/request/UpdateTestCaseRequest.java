package io.javakata.core.testcase.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTestCaseRequest {

    // null이면 새로운 테스트 케이스
    private Long id;

    @NotNull
    @NotBlank(message = "입력값은 필수입니다.")
    private String input;

    @NotNull
    @NotBlank(message = "기대 출력값은 필수입니다.")
    private String expectedOutput;

    private boolean isPublic;

}