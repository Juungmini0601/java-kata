package io.javakata.storage.db.core.problem;

import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProblemListSearchParam {

    private Pageable pageable;

    private String keyword;

    private List<String> categories;

    private List<String> levels;

}
