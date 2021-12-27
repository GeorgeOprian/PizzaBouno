package ro.unibuc.springlab8example1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u FROM User u WHERE u.userType = :userType AND u.fullName = :fullName")
    List<User> filter(UserType userType, String fullName);

    @Modifying
    @Query(value = "DELETE FROM User u WHERE u.accountCreated < :time")
    void deleteOlderUsers(LocalDateTime time);
}
