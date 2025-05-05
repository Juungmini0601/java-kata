package io.javakata.storage.db.core.problem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.javakata.model.problem.Level;
import io.javakata.model.problem.ProblemWithCategory;
import io.javakata.storage.db.core.problem.category.QProblemCategoryEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProblemSearchRepositoryImpl implements ProblemSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QProblemEntity problem = QProblemEntity.problemEntity;

    private final QProblemCategoryEntity category = QProblemCategoryEntity.problemCategoryEntity;

    @Override
    public Page<ProblemWithCategory> getProblems(ProblemListSearchParam param) {
        Pageable pageable = param.getPageable();

        BooleanBuilder builder = new BooleanBuilder();

        // 키워드 검색 조건
        if (param.getKeyword() != null && !param.getKeyword().isEmpty()) {
            builder.and(problem.title.containsIgnoreCase(param.getKeyword()));
        }

        // 카테고리 검색 조건
        if (param.getCategories() != null && !param.getCategories().isEmpty()) {
            builder.and(category.name.in(param.getCategories()));
        }

        // 난이도 검색 조건
        if (param.getLevels() != null && !param.getLevels().isEmpty()) {
            builder.and(problem.level.stringValue().in(param.getLevels()));
        }

        // 전체 쿼리 실행
        List<ProblemWithCategory> result = queryFactory
            .select(Projections.constructor(ProblemWithCategory.class, problem.id, problem.title,
                    problem.level.stringValue(), category.name.as("categoryName"), problem.createdAt,
                    problem.updatedAt))
            .from(problem)
            .join(category)
            .on(problem.categoryId.eq(category.id))
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 총 개수 쿼리
        Long totalCount = queryFactory.select(problem.count())
            .from(problem)
            .join(category)
            .on(problem.categoryId.eq(category.id))
            .where(builder)
            .fetchOne();

        totalCount = (totalCount == null) ? 0 : totalCount;

        return new PageImpl<>(result, pageable, totalCount);
    }

    @Override
    public List<Level> getDistinctLevels() {
        return queryFactory.select(problem.level).distinct().from(problem).fetch();
    }

}