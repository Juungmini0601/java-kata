package io.javakata.storage.db.core.problem.category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : kimjungmin Created on : 2025. 5. 1.
 */
public interface ProblemCategoryRepository extends JpaRepository<ProblemCategoryEntity, Long> {

    boolean existsByName(final String name);

}
