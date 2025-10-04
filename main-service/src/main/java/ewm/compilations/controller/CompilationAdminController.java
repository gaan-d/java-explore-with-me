package ewm.compilations.controller;

import ewm.compilations.dto.CompilationCreateDto;
import ewm.compilations.dto.CompilationDto;
import ewm.compilations.dto.CompilationUpdateDto;
import ewm.compilations.service.CompilationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class CompilationAdminController {
    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid CompilationCreateDto compilationCreateDto) {
        return service.addCompilation(compilationCreateDto);
    }

    @PatchMapping("/{compilationId}")
    public CompilationDto updateCompilation(@PathVariable Long compilationId,
                                            @RequestBody @Valid CompilationUpdateDto updateDto) {
        return service.updateCompilation(compilationId, updateDto);
    }

    @DeleteMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compilationId) {
        service.deleteCompilation(compilationId);
    }
}
