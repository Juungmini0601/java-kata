package io.javakata.storage.db.core.problem.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemCategoryJPARepository extends JpaRepository<ProblemCategoryEntity, Long> {

    boolean existsByName(final String name);

}
