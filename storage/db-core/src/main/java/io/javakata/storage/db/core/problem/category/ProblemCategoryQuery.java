package io.javakata.storage.db.core.problem.category;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.ProblemCategory;
import io.javakata.storage.db.core.error.ConflictException;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
@Repository
@RequiredArgsConstructor
public class ProblemCategoryQuery {

    private final ProblemCategoryRepository problemCategoryRepository;

    @Transactional(readOnly = true)
    public void ifExistsByNameDoThrow(final String categoryName) {
        if (problemCategoryRepository.existsByName(categoryName)) {
            throw new ConflictException("duplicated category name:" + categoryName);
        }
    }

    @Transactional(readOnly = true)
    public ProblemCategory findByIdOrElseThrow(final Long id) {
        return problemCategoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("not found category id:" + id))
            .toModel();
    }

    public List<ProblemCategory> findAll() {
        return problemCategoryRepository.findAll().stream().map(ProblemCategoryEntity::toModel).toList();
    }

}
