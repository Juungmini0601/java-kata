package io.javakata.storage.db.core.problem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProblemRepository extends JpaRepository<ProblemEntity, Long>, ProblemSearchRepository {

    @Query("SELECT p FROM ProblemEntity p JOIN FETCH p.testCases WHERE p.id = :problemId")
    Optional<ProblemEntity> findByIdFetchJoin(Long problemId);

}
