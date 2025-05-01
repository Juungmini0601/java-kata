package io.javakata.core.problem.category.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.ProblemCategory;
import io.javakata.storage.db.core.problem.category.ProblemCategoryCommand;
import io.javakata.storage.db.core.problem.category.ProblemCategoryQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemCategoryService {
	private final ProblemCategoryCommand problemCategoryCommand;
	private final ProblemCategoryQuery problemCategoryQuery;

	@Transactional(readOnly = true)
	public List<ProblemCategory> findAll() {
		return problemCategoryQuery.findAll();
	}

	@Transactional
	public ProblemCategory createCategory(final String categoryName) {
		problemCategoryQuery.ifExistsByNameDoThrow(categoryName);
		ProblemCategory problemCategory = ProblemCategory.from(categoryName);

		return problemCategoryCommand.save(problemCategory);
	}

	@Transactional
	public ProblemCategory updateCategory(final Long categoryId, final String categoryName) {
		ProblemCategory problemCategory = problemCategoryQuery.findByIdOrElseThrow(categoryId);
		problemCategory.changeName(categoryName);

		return problemCategoryCommand.save(problemCategory);
	}

	@Transactional
	public void deleteCategory(final Long categoryId) {
		problemCategoryCommand.deleteById(categoryId);
	}
}
