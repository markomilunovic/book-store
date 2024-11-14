package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.RefreshToken;
import com.bookstore.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    List<RefreshToken> findAllByAccessToken_User(User user);

}

