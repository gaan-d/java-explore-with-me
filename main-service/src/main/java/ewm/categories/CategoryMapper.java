package ewm.categories;

import ewm.categories.dto.CategoryCreateDto;
import ewm.categories.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category mapCategoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto) {
        return Category.builder().name(categoryCreateDto.getName()).build();
    }

    public CategoryDto mapCategoryToCategoryDto(Category category) {
        return CategoryDto.builder().id(category.getId()).name(category.getName()).build();
    }
}
