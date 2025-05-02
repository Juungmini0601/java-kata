package io.javakata.storage.db.core.testcase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCaseEntity, Long> {

    void deleteAllByIdIn(List<Long> ids);

}
