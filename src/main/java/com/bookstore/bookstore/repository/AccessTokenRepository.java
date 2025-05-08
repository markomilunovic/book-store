package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.entity.AccessToken;
import com.bookstore.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

    List<AccessToken> findAllByUser(User user);

}

