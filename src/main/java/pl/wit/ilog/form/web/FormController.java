package pl.wit.ilog.form.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.wit.ilog.form.model.FormEntity;
import pl.wit.ilog.form.model.IFormRepo;
import pl.wit.ilog.internals.exception.EntityNotFoundException;
import pl.wit.ilog.internals.web.IMapper;
import pl.wit.ilog.security.CurrentUser;
import pl.wit.ilog.security.jwt.UserPrincipal;
import pl.wit.ilog.user.model.IUserRepository;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final IFormRepo formRepo;

    private final IUserRepository userRepository;

    private final IMapper<FormEntity, FormResponse> mapper;

    @Transactional
    @GetMapping("/{uuid}")
    public FormResponse getForm(@NotNull @PathVariable final UUID uuid) {
        return formRepo.findByUuid(uuid)
                .map(mapper::map)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public List<FormResponse> getAllForms(@CurrentUser UserPrincipal currentUser) {
        val user = userRepository.findById(currentUser.getId())
                .orElseThrow(EntityNotFoundException::new);
        return formRepo.findAllByCreatedBy(user)
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<FormResponse> create(@RequestBody @NotNull final FormCreateRequest request,
                                               @CurrentUser UserPrincipal currentUser) throws Exception {
        val form = new FormEntity();

        val user = userRepository.findById(currentUser.getId())
                .orElseThrow(EntityNotFoundException::new);

        form.setCreatedBy(user);
        form.setUuid(UUID.randomUUID());
        form.setDate(new Date());
        form.setName(request.getFormName());

        formRepo.save(form);
        val response = mapper.map(form);
        return ResponseEntity.created(new URI("/forms/" + response.getUuid().toString()))
                .body(response);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{formUuid}")
    void delete(@NotNull @PathVariable final UUID uuid) {
        val form = formRepo.findByUuid(uuid)
                .orElseThrow(EntityNotFoundException::new);
        formRepo.delete(form);
    }
}
