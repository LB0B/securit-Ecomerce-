package net.arkx.userservice.repository;

import net.arkx.userservice.entities.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Long> {

    Optional<Jwt> findByValueAndDeactivateAndExpire(String value, boolean disable, boolean expire);
    @Query("FROM Jwt j WHERE j.expire = :expire AND j.deactivate = :disable AND j.user.username = :username")
    Optional<Jwt> findUserValidToken(String username, boolean disable, boolean expire);

    @Query("FROM Jwt j WHERE  j.user.username = :username")
    Stream<Jwt> findUser(String username);

    @Query("FROM Jwt j WHERE  j.refreshToken.value = :value")
    Optional<Jwt> findByRefreshToken(String value);
    void deleteAllByExpireAndDeactivate(boolean expire, boolean disable);

}
