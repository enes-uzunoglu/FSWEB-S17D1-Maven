package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workintech/animal")
public class AnimalController {

    private final Map<Integer, Animal> animals = new HashMap<>();

    @GetMapping()
    public List<Animal> getAnimalList() {
        // animals.values() toList() yerine stream() kullanarak listeyi döndürüyoruz.
        return animals.values().stream().collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable int id) {
        Animal animal = animals.get(id);
        if (animal != null) {
            return ResponseEntity.ok(animal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 döndürüyoruz
    }

    @PostMapping()
    public ResponseEntity<Animal> postAnimal(@RequestBody Animal animal) {
        if (animal.getId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // ID kontrolü ekledik
        }

        animals.put(animal.getId(), animal);
        return ResponseEntity.status(HttpStatus.CREATED).body(animal); // 201 Created döndürüyoruz
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable int id, @RequestBody Animal animal) {
        if (!animals.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 döndür
        }

        // Gerekli güncellemeyi yapıyoruz
        animals.put(id, animal);
        return ResponseEntity.ok(animal); // Güncellenmiş hayvanı döndürüyoruz
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable int id) {
        if (!animals.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 döndür
        }

        animals.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content döndür
    }
}
