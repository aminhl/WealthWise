package com.nexthope.wealthwise_backend.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
        select t from Token t inner join User u on t.user.Id = u.Id
        where u.Id = :userId and (t.isExpired = false and t.isRevoked = false)
        """)
    List<Token> findAllValidTokensByUser(Integer userId);
    Optional<Token> findTokenByToken(String token);
}
