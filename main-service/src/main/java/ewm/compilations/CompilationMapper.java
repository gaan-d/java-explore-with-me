package ewm.compilations;

import ewm.compilations.dto.CompilationCreateDto;
import ewm.compilations.dto.CompilationDto;
import org.springframework.stereotype.Component;

@Component
public class CompilationMapper {
    public Compilation mapCompilationCreateDtoToCompilation(CompilationCreateDto createDto) {
        return Compilation.builder()
                .title(createDto.getTitle())
                .pinned(createDto.isPinned())
                .build();
    }

    public CompilationDto mapCompilationToCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
    }
}
