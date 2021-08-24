package net.springboot.javaguides.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.springboot.javaguides.entity.DoanhNghiep;

@Repository
public interface DoanhNghiepRepository extends JpaRepository<DoanhNghiep, Long>{
	//Tim doanh nghiep theo user_id
	@Query(value = "select * from doanhnghiep where user_id= ?1",nativeQuery = true)
	DoanhNghiep findbyUserId(Long id);
	
	//Tim doanh nghiep theo ma thue
		@Query(value = "select * from doanhnghiep where mathue= ?1",nativeQuery = true)
		List<DoanhNghiep> findbyMaThue(String maThue);
}
