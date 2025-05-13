package io.javakata.core.problem.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.Problem;
import io.javakata.model.problem.ProblemWithCategory;
import io.javakata.model.testcase.TestCase;
import io.javakata.storage.db.core.problem.ProblemListSearchParam;
import io.javakata.storage.db.core.problem.ProblemRepository;
import io.javakata.storage.db.core.testcase.TestCaseRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    private final TestCaseRepository testCaseRepository;

    @Transactional(readOnly = true)
    public Problem findById(final Long id) {
        return problemRepository.findByIdWithTestCase(id);
    }

    @Transactional
    public Problem createProblem(CreateProblemCommand problemCommand,
            List<ProblemService.CreateTestCaseCommand> testCaseCommands) {
        Problem problem = problemCommand.toModel();
        List<TestCase> testCases = testCaseCommands.stream()
            .map(ProblemService.CreateTestCaseCommand::toModel)
            .toList();

        testCases.forEach(problem::addTestCase);

        return this.problemRepository.save(problem);
    }

    @Transactional
    public Problem updateProblem(UpdateProblemCommand problemCommand,
            List<UpdateTestCaseCommand> updateTestCaseCommands) {
        Problem problem = problemRepository.findByIdWithTestCase(problemCommand.id);
        Problem updateInfo = problemCommand.toModel();
        List<TestCase> updateTestCases = updateTestCaseCommands.stream().map(UpdateTestCaseCommand::toModel).toList();
        updateTestCases.forEach(updateInfo::addTestCase);

        List<Long> testCaseIdsToRemove = problem.getTestCasesToRemove(updateTestCases)
            .stream()
            .map(TestCase::getId)
            .toList();

        problem.update(updateInfo);

        testCaseRepository.deleteAllById(testCaseIdsToRemove);
        return this.problemRepository.save(problem);
    }

    @Transactional(readOnly = true)
    public Page<ProblemWithCategory> fetchProblemList(ProblemListSearchParam param) {
        return problemRepository.getProblems(param);
    }

    @Transactional(readOnly = true)
    public List<String> getDistinctLevels() {
        return problemRepository.getDistinctLevels().stream().map(Object::toString).toList();
    }

    @Transactional
    public void deleteProblem(final Long problemId) {
        problemRepository.deleteById(problemId);
    }

    @Builder
    public record CreateProblemCommand(String title, Level level, String description, String constraints, String input,
            String expectedOutput, String baseCode, Long memoryLimitMB, Long timeLimitMS, Long categoryId) {

        public Problem toModel() {
            return Problem.builder()
                .title(title())
                .level(level())
                .description(description())
                .constraints(constraints())
                .input(input())
                .expectedOutput(expectedOutput())
                .baseCode(baseCode())
                .memoryLimitMB(memoryLimitMB())
                .timeLimitMS(timeLimitMS())
                .categoryId(categoryId())
                .build();
        }
    }

    @Builder
    public record UpdateProblemCommand(Long id, String title, Level level, String description, String constraints,
            String input, String expectedOutput, String baseCode, Long memoryLimitMB, Long timeLimitMS,
            Long categoryId) {

        public Problem toModel() {
            return Problem.builder()
                .id(id())
                .title(title())
                .level(level())
                .description(description())
                .constraints(constraints())
                .input(input())
                .expectedOutput(expectedOutput())
                .baseCode(baseCode())
                .memoryLimitMB(memoryLimitMB())
                .timeLimitMS(timeLimitMS())
                .categoryId(categoryId())
                .build();
        }
    }

    @Builder
    public record CreateTestCaseCommand(String input, String expectedOutput, boolean isPublic) {
        public TestCase toModel() {
            return TestCase.builder().input(input()).expectedOutput(expectedOutput()).isPublic(isPublic()).build();
        }
    }

    @Builder
    public record UpdateTestCaseCommand(Long id, String input, String expectedOutput, boolean isPublic,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        public TestCase toModel() {
            return TestCase.builder()
                .id(id)
                .input(input())
                .expectedOutput(expectedOutput())
                .isPublic(isPublic())
                .createdAt(createdAt())
                .updatedAt(updatedAt())
                .build();
        }
    }

}
