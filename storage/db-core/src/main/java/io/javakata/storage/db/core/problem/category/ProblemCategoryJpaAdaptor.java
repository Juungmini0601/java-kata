package io.javakata.storage.db.core.problem.category;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.javakata.model.problem.ProblemCategory;
import io.javakata.storage.db.core.error.ConflictException;
import io.javakata.storage.db.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemCategoryJpaAdaptor implements ProblemCategoryRepository {

    private final ProblemCategoryJPARepository problemCategoryJPARepository;

    @Transactional
    @Override
    public ProblemCategory save(ProblemCategory category) {
        ProblemCategoryEntity problemCategoryEntity = ProblemCategoryEntity.from(category);

        return problemCategoryJPARepository.save(problemCategoryEntity).toModel();
    }

    @Transactional
    @Override
    public void deleteById(Long categoryId) {
        problemCategoryJPARepository.deleteById(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProblemCategory> findAll() {
        return problemCategoryJPARepository.findAll().stream().map(ProblemCategoryEntity::toModel).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProblemCategory findByIdOrElseThrow(Long id) {
        return problemCategoryJPARepository.findById(id)
            .orElseThrow(() -> new NotFoundException("not found category id:" + id))
            .toModel();
    }

    @Transactional(readOnly = true)
    @Override
    public void ifExistsByNameDoThrow(String categoryName) {
        if (problemCategoryJPARepository.existsByName(categoryName)) {
            throw new ConflictException("duplicated category name:" + categoryName);
        }
    }

}
