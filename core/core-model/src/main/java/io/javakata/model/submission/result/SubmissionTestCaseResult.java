package io.javakata.model.submission.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionTestCaseResult {

    private Long submitId;

    private Long testCaseId;

    private Long userId;

    private String status;

    private String input;

    private String output;

    private String expectedOutput;

    private String error;

    private Long usedMemoryMB;

    private Long executionTimeMS;

}
