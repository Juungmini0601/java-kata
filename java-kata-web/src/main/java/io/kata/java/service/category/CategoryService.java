package io.kata.java.service.category;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.generated.tables.pojos.Categories;
import org.springframework.stereotype.Service;

import io.kata.java.core.repository.category.CategoryCommand;
import io.kata.java.core.repository.category.CategoryQuery;
import io.kata.java.error.ErrorType;
import io.kata.java.error.JavaKataException;
import io.kata.java.service.category.mapper.CategoryMapper;
import io.kata.java.service.category.model.CreateCategoryInfo;
import io.kata.java.service.category.model.UpdateCategoryInfo;
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

	public Categories findById(Long id) {
		return categoryQuery.findOneById(id)
			.orElseThrow(() -> new JavaKataException(ErrorType.VALIDATION_ERROR, id + "에 해당 하는 카테고리를 찾을 수 없습니다."));
	}

	public void create(String name, String description) {
		CreateCategoryInfo createCategoryInfo = new CreateCategoryInfo(name, description);
		Categories categories = CategoryMapper.INSTANCE.toEntity(createCategoryInfo);

		categoryCommand.save(categories);
	}

	public void update(Long id, String name, String description) {
		UpdateCategoryInfo updateCategoryInfo = new UpdateCategoryInfo(id, name, description, LocalDateTime.now());
		Categories categories = CategoryMapper.INSTANCE.toEntity(updateCategoryInfo);

		categoryCommand.updateOne(categories);
	}

	public void delete(Long id) {
		categoryCommand.deleteOne(id);
	}
}
