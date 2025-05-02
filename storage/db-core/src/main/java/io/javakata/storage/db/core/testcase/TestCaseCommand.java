package io.javakata.storage.db.core.testcase;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author : kimjungmin Created on : 2025. 5. 2.
 */
@Repository
@RequiredArgsConstructor
public class TestCaseCommand {

    private final TestCaseRepository testCaseRepository;

    @Transactional
    public void deleteAllById(List<Long> ids) {
        testCaseRepository.deleteAllById(ids);
    }

}
