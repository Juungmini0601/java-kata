package io.javakata.storage.db.core.problem;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.Problem;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemCommand {

    private final ProblemRepository problemRepository;

    @Transactional
    public Problem save(Problem problem) {
        ProblemEntity problemEntity = ProblemEntity.from(problem);

        return problemRepository.save(problemEntity).toModel();
    }

    @Transactional
    public void deleteById(final Long problemId) {
        problemRepository.deleteById(problemId);
    }

}
