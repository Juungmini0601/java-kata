package io.javakata.storage.db.core.problem.category;

import java.util.List;

import io.javakata.model.problem.ProblemCategory;

public interface ProblemCategoryRepository {

    ProblemCategory save(ProblemCategory category);

    void deleteById(final Long categoryId);

    List<ProblemCategory> findAll();

    ProblemCategory findByIdOrElseThrow(final Long id);

    void ifExistsByNameDoThrow(final String categoryName);

}
