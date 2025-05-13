package io.javakata.core.problem.category.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.ProblemCategory;
import io.javakata.storage.db.core.problem.category.ProblemCategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemCategoryService {

    private final ProblemCategoryRepository problemCategoryRepository;

    @Transactional(readOnly = true)
    public List<ProblemCategory> findAll() {
        return problemCategoryRepository.findAll();
    }

    @Transactional
    public ProblemCategory createCategory(final String categoryName) {
        problemCategoryRepository.ifExistsByNameDoThrow(categoryName);
        ProblemCategory problemCategory = ProblemCategory.from(categoryName);

        return problemCategoryRepository.save(problemCategory);
    }

    @Transactional
    public ProblemCategory updateCategory(final Long categoryId, final String categoryName) {
        ProblemCategory problemCategory = problemCategoryRepository.findByIdOrElseThrow(categoryId);
        problemCategory.changeName(categoryName);

        return problemCategoryRepository.save(problemCategory);
    }

    @Transactional
    public void deleteCategory(final Long categoryId) {
        problemCategoryRepository.deleteById(categoryId);
    }

}
