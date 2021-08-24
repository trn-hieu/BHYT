package net.springboot.javaguides.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.springboot.javaguides.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	
	@Query(value = "SELECT CASE WHEN EXISTS ( SELECT * FROM user WHERE user.email=?1) THEN true ELSE false END AS bool;",nativeQuery = true)
	BigInteger isEmailExist(String email);
}
