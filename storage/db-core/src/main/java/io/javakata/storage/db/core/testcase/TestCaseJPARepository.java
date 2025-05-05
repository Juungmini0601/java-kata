package io.javakata.storage.db.core.testcase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseJPARepository extends JpaRepository<TestCaseEntity, Long> {

}
