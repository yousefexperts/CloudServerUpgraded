package co.insight.starter.firebase.repository;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import co.insight.starter.firebase.domain.PersistentToken;
import co.insight.starter.firebase.domain.User;

import java.util.List;


public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
