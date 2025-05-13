package io.javakata.storage.db.core.problem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.Problem;
import io.javakata.model.problem.ProblemWithCategory;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemJPAAdaptor implements ProblemRepository {

    private final ProblemJPARepository problemJPARepository;

    @Override
    public Problem save(Problem problem) {
        ProblemEntity problemEntity = ProblemEntity.from(problem);

        return problemJPARepository.save(problemEntity).toModel();
    }

    @Override
    public void deleteById(Long problemId) {
        problemJPARepository.deleteById(problemId);
    }

    @Override
    public List<Level> getDistinctLevels() {
        return problemJPARepository.getDistinctLevels();
    }

    @Override
    public Problem findByIdWithTestCase(Long problemId) {
        return problemJPARepository.findByIdFetchJoin(problemId)
            .orElseThrow(() -> new NotFoundException("Notfound Problem problemId: " + problemId))
            .toModel();
    }

    @Override
    public Page<ProblemWithCategory> getProblems(ProblemListSearchParam param) {
        return problemJPARepository.getProblems(param);
    }

}
