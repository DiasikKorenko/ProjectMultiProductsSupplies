package com.exampl.repository;

import com.exampl.domain.Admin;
import com.exampl.domain.Cart;
import com.exampl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByNumber(  String number);

   /* Cart findByUserId(User user);*/
   List<Cart> findByUserId(int userId);

}
