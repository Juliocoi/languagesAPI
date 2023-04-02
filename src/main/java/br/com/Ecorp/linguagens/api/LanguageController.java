package br.com.Ecorp.linguagens.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguageController {
  @Autowired
  private LanguagesRepository repository;

  @GetMapping("/rankOfLanguages")
  public List<Languages> getLanguagens() {
    List<Languages> languages = repository.findAll(Sort.by(Direction.ASC, "ranking"));
    return languages;

  }

  @PostMapping("/languages")
  public ResponseEntity<String> postLanguage(@RequestBody Languages newLanguage) {
    repository.save(newLanguage);
    return ResponseEntity.status(201).build();
  }

  @PutMapping("update/{id}")
  public Languages update(@RequestBody Languages updateLanguege, @PathVariable String id) {
    return repository.findById(id)
        .map(language -> {
          language.setTitle(updateLanguege.getTitle());
          language.setImage(updateLanguege.getImage());
          language.setRanking(updateLanguege.getRanking());
          return repository.save(language);
        }).orElseGet(() -> {
          updateLanguege.setId(id);
          return repository.save(updateLanguege);
        });
  }

  @DeleteMapping("/language/{id}")
  public ResponseEntity<Object> delete(@PathVariable String id){
    repository.deleteById(id);
    return ResponseEntity.status(200).build();
  }
}
