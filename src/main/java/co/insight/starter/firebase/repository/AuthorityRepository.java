package co.insight.starter.firebase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.insight.starter.firebase.domain.Authority;


public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
