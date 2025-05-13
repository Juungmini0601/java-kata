package io.javakata.storage.db.core.problem;

import java.util.List;

import org.springframework.data.domain.Page;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.ProblemWithCategory;

public interface ProblemSearchRepository {

    Page<ProblemWithCategory> getProblems(ProblemListSearchParam param);

    List<Level> getDistinctLevels();

}