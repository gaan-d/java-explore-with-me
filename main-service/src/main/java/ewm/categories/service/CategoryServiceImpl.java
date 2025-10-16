package ewm.categories.service;

import ewm.categories.Category;
import ewm.categories.CategoryMapper;
import ewm.categories.CategoryRepository;
import ewm.categories.dto.CategoryCreateDto;
import ewm.categories.dto.CategoryDto;
import ewm.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto create(CategoryCreateDto createDto) {
        Category category = mapper.mapCategoryCreateDtoToCategory(createDto);
        return mapper.mapCategoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(long catId, CategoryCreateDto createDto) {
        Category category = checkCategoryExistence(catId);
        category.setName(createDto.getName());
        return mapper.mapCategoryToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(long catId) {
        checkCategoryExistence(catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).map(mapper::mapCategoryToCategoryDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(long catId) {
        Category category = checkCategoryExistence(catId);
        return mapper.mapCategoryToCategoryDto(category);
    }

    @Override
    public Category checkCategoryExistence(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("Category with id " + catId + " not found"));
    }
}
