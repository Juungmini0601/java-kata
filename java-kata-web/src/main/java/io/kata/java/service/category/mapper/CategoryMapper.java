package io.kata.java.service.category.mapper;

import org.jooq.generated.tables.pojos.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import io.kata.java.service.category.model.CreateCategoryInfo;

/**
 * @author    : kimjungmin
 * Created on : 2025. 2. 26.
 */
@Mapper
public interface CategoryMapper {
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	Categories toEntity(CreateCategoryInfo createCategoryInfo);
}
