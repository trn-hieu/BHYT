package net.springboot.javaguides.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.HoGiaDinh;

@Repository
public interface HoGiaDinhRepository extends JpaRepository<HoGiaDinh, Long>{
	//Tim Ca nhan trong Ho gi dinh = id User
	@Query(value = "select * from canhan where hogiadinh_id=(select hogiadinh_id from canhan where user_id=?1)",nativeQuery = true)
	List<CaNhan> findAllHGDbyUserId(Long id);
	
}
