package ewm.categories.controller;

import ewm.categories.dto.CategoryCreateDto;
import ewm.categories.dto.CategoryDto;
import ewm.categories.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody @Valid CategoryCreateDto categoryDto) {
        log.info("Creating category: {}", categoryDto);
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@PathVariable Long catId, @RequestBody @Valid CategoryCreateDto createDto) {
        log.info("Updating category with catId: {}, createDto: {}", catId, createDto);
        return categoryService.update(catId, createDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long catId) {
        log.info("Deleting category with catId: {}", catId);
        categoryService.deleteById(catId);
    }
}
