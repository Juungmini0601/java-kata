package io.kata.java.service.category;

import java.util.List;

import org.jooq.generated.tables.pojos.Categories;
import org.springframework.stereotype.Service;

import io.kata.java.core.repository.category.CategoryCommand;
import io.kata.java.core.repository.category.CategoryQuery;
import io.kata.java.service.category.mapper.CategoryMapper;
import io.kata.java.service.category.model.CreateCategoryInfo;
import lombok.RequiredArgsConstructor;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 23.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryQuery categoryQuery;
	private final CategoryCommand categoryCommand;

	public List<Categories> findAll() {
		return categoryQuery.getAllCategories();
	}

	public void create(String name, String description) {
		CreateCategoryInfo createCategoryInfo = new CreateCategoryInfo(name, description);
		Categories categories = CategoryMapper.INSTANCE.toEntity(createCategoryInfo);

		categoryCommand.save(categories);
	}
}
