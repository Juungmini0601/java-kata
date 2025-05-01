package io.javakata.storage.db.core.problem.category;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.ProblemCategory;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemCategoryCommand {

    private final ProblemCategoryRepository problemCategoryRepository;

    @Transactional
    public ProblemCategory save(ProblemCategory category) {
        ProblemCategoryEntity problemCategoryEntity = ProblemCategoryEntity.from(category);

        return problemCategoryRepository.save(problemCategoryEntity).toModel();
    }

    @Transactional
    public void deleteById(final Long categoryId) {
        problemCategoryRepository.deleteById(categoryId);
    }

}
