package io.javakata.storage.db.core.problem;

import java.util.List;

import org.springframework.data.domain.Page;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.Problem;
import io.javakata.model.problem.ProblemWithCategory;

public interface ProblemRepository {

    Problem save(Problem problem);

    void deleteById(final Long problemId);

    List<Level> getDistinctLevels();

    Problem findByIdWithTestCase(final Long problemId);

    Page<ProblemWithCategory> getProblems(ProblemListSearchParam param);

}
