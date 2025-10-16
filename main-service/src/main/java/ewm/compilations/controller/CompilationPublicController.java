package ewm.compilations.controller;

import ewm.compilations.dto.CompilationDto;
import ewm.compilations.service.CompilationService;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/compilations")
public class CompilationPublicController {
    private final CompilationService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получаем подборки pinned: {}, from: {}, size: {}", pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compilationId}")
    public CompilationDto getCompilationById(@PathVariable Long compilationId) {
        log.info("Получаем подборку по id: {}", compilationId);
        return service.getCompilationById(compilationId);
    }
}
