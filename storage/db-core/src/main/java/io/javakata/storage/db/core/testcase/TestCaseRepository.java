package io.javakata.storage.db.core.testcase;

import java.util.List;

public interface TestCaseRepository {

    void deleteAllById(List<Long> ids);

}
