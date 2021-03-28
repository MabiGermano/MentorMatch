package br.com.dextra.bootcamp.MentorMatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dextra.bootcamp.MentorMatch.models.Mentored;

public interface MentoredRepository extends JpaRepository<Mentored, Long> {}
