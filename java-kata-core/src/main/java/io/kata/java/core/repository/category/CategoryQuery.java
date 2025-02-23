package io.kata.java.core.repository.category;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.generated.tables.JCategories;
import org.jooq.generated.tables.pojos.Categories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 23.
 */
@Repository
@RequiredArgsConstructor
public class CategoryQuery {

	private final DSLContext dslContext;
	private final JCategories CATEGORY = JCategories.CATEGORIES;

	@Transactional(readOnly = true)
	public List<Categories> getAllCategories() {
		return dslContext.selectFrom(CATEGORY)
			.fetchInto(Categories.class);
	}
}
