package io.javakata.storage.db.core.problem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.Problem;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemQuery {

    private final ProblemRepository problemRepository;

    @Transactional(readOnly = true)
    public List<Level> getDistinctLevels() {
        return problemRepository.getDistinctLevels();
    }

    @Transactional(readOnly = true)
    public Page<ProblemWithCategory> getProblems(ProblemListSearchParam param) {
        return problemRepository.getProblems(param);
    }

    @Transactional(readOnly = true)
    public Problem findByIdWithTestCase(final Long problemId) {
        return problemRepository.findByIdFetchJoin(problemId)
            .orElseThrow(() -> new NotFoundException("Notfound Problem problemId: " + problemId))
            .toModel();
    }

}
