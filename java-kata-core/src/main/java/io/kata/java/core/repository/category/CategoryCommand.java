package io.kata.java.core.repository.category;

import org.jooq.DSLContext;
import org.jooq.generated.tables.JCategories;
import org.jooq.generated.tables.pojos.Categories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 26.
 */
@Repository
@RequiredArgsConstructor
public class CategoryCommand {

	private final DSLContext dslContext;
	private final JCategories CATEGORY = JCategories.CATEGORIES;

	@Transactional
	public Long save(Categories category) {
		return dslContext.insertInto(CATEGORY,
				CATEGORY.NAME,
				CATEGORY.DESCRIPTION)
			.values(category.getName(), category.getDescription())
			.returningResult(CATEGORY.ID)
			.fetchOneInto(Long.class);
	}

	@Transactional
	public Categories updateOne(Categories category) {
		return dslContext.update(CATEGORY)
			.set(CATEGORY.NAME, category.getName())
			.set(CATEGORY.DESCRIPTION, category.getDescription())
			.where(CATEGORY.ID.eq(category.getId()))
			.returning()
			.fetchOneInto(Categories.class);
	}
}
