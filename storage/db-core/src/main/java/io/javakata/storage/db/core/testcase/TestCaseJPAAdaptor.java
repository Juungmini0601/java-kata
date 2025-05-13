package io.javakata.storage.db.core.testcase;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TestCaseJPAAdaptor implements TestCaseRepository {

    private final TestCaseJPARepository testCaseJPARepository;

    @Transactional
    public void deleteAllById(List<Long> ids) {
        testCaseJPARepository.deleteAllById(ids);
    }

}
