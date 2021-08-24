package net.springboot.javaguides.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.springboot.javaguides.entity.CaNhan;

@Repository
public interface CaNhanRepository extends JpaRepository<CaNhan, Long> {
	@Query(value = "select * from canhan c where c.user_id=?1", nativeQuery = true)
	CaNhan findbyUserId(Long id);
	
	//Tim Ca nhan trong Ho gi dinh = id User
		@Query(value = "select * from canhan where hogiadinh_id=(select hogiadinh_id from canhan where user_id=?1)",nativeQuery = true)
		List<CaNhan> findAllHGDbyUserId(Long id);
		
	//Xoa canhan khỏi hộ gia đình
		@Modifying
		@Transactional
		@Query(value = "update canhan set canhan.hogiadinh_id=null where canhan.id=?1",nativeQuery = true)
		void deleteHGDbyid(long id);
	
	// tim Ca nhan = ma BHXH
	@Query(value = "select * from canhan where maBHXH=?",nativeQuery = true)
	CaNhan findByMaBHXH(String maBHXH);
}


