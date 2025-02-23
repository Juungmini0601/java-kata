package io.kata.java.service.category;

import java.util.List;

import org.jooq.generated.tables.pojos.Categories;
import org.springframework.stereotype.Service;

import io.kata.java.core.repository.category.CategoryQuery;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 23.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryQuery categoryQuery;

	public List<Categories> findAll() {
		return categoryQuery.getAllCategories();
	}
}
