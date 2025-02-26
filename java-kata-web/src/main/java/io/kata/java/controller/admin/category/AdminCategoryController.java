package io.kata.java.controller.admin.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.kata.java.controller.admin.category.request.CreateCategoryRequest;
import io.kata.java.controller.admin.category.request.UpdateCategoryRequest;
import io.kata.java.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. 카테고리 인덱스 UI 개발 완료
 * 2. 카테고리 리스트 조회 기능 개발 완료
 * 3. 카테고리 생성 기능 개발 예정 -> 모달 하나로 공통 처리 할 것
 * 4. 카테고리 수정 기능 개발 예정
 * 5. 카테고리 삭제 기능 개발 예정
 */
@Slf4j
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
	public String createForm(@ModelAttribute("createCategoryRequest") CreateCategoryRequest createCategoryRequest) {

		return "admin/category/createForm";
	}

	@PostMapping("/admin/category/create")
	public String create(
		@Valid @ModelAttribute("createCategoryRequest") CreateCategoryRequest createCategoryRequest,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			log.error("Validation error: {}", bindingResult.getAllErrors());
			return "admin/category/createForm";
		}

		categoryService.create(createCategoryRequest.name(), createCategoryRequest.description());

		return "redirect:/admin/category/index";
	}

	@GetMapping("/admin/category/{id}/updateForm")
	public String updateForm(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("updateCategoryRequest", categoryService.findById(id));

		return "admin/category/updateForm";
	}

	@PostMapping("/admin/category/update")
	public String update(
		// TODO ModelAttribute와 BindingResult 파라미터 순서 블로깅
		@Valid @ModelAttribute("updateCategoryRequest") UpdateCategoryRequest updateCategoryRequest,
		BindingResult bindingResult,
		Model model) {

		if (bindingResult.hasErrors()) {
			log.error("Validation error: {}", bindingResult.getAllErrors());
			// th:object와 Model Attribute가 매핑 되지 않아서 버그 생김
			model.addAttribute("category", updateCategoryRequest);
			return "admin/category/updateForm";
		}

		categoryService.update(
			updateCategoryRequest.id(),
			updateCategoryRequest.name(),
			updateCategoryRequest.description()
		);

		return "redirect:/admin/category/index";
	}

	// /admin/category/{id}/delete admin/category/1/delete/

	@PostMapping("/admin/category/{id}/delete")
	public String delete(@PathVariable(name = "id") Long id) {
		categoryService.delete(id);

		return "redirect:/admin/category/index";
	}
}
