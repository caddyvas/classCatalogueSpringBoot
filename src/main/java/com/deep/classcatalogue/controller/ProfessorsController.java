package com.deep.classcatalogue.controller;

import com.deep.classcatalogue.classModel.Professors;
import com.deep.classcatalogue.repository.ProfessorRepository;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfessorsController {
    
    private ProfessorRepository professorRepository;

    public ProfessorsController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

@GetMapping("/professorsList")
    public Iterable<Professors> getAllProfessors() {
        return this.professorRepository.findAll();
    }

}
