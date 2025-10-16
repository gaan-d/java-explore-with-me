package ewm.categories.service;

import ewm.categories.Category;
import ewm.categories.dto.CategoryCreateDto;
import ewm.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryCreateDto createDto);

    CategoryDto update(long catId, CategoryCreateDto createDto);

    void deleteById(long catId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getById(long catId);

    Category checkCategoryExistence(long catId);
}
