package br.com.dextra.bootcamp.MentorMatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dextra.bootcamp.MentorMatch.models.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Long> {}
