package io.kata.java.controller.admin.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.kata.java.controller.admin.category.request.CreateCategoryRequest;
import io.kata.java.service.category.CategoryService;
import lombok.RequiredArgsConstructor;

/**
 * 1. 카테고리 인덱스 UI 개발 완료
 * 2. 카테고리 리스트 조회 기능 개발 완료
 * 3. 카테고리 생성 기능 개발 예정 -> 모달 하나로 공통 처리 할 것
 * 4. 카테고리 수정 기능 개발 예정
 * 5. 카테고리 삭제 기능 개발 예정
 */
@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

	private final CategoryService categoryService;

	@GetMapping("/admin/category/index")
	public String index(Model model) {
		model.addAttribute("categories", categoryService.findAll());

		return "admin/category/index";
	}

	@GetMapping("/admin/category/createForm")
	public String create(@ModelAttribute("createCategoryRequest") CreateCategoryRequest createCategoryRequest) {
		
		return "admin/category/createForm";
	}
}
