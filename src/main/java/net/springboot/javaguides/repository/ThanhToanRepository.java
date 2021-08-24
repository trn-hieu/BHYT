package net.springboot.javaguides.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.springboot.javaguides.entity.ThanhToan;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long>{
	
	@Query(value = "select thanhtoan.id,thanhtoan.magiaodich,thanhtoan.noidung,thanhtoan.phuongthuc,thanhtoan.sohoadon,thanhtoan.sotien,thanhtoan.thoigian "
			+ "from thanhtoan, (select thanhtoan_id from user_payment where user_id=?1) AS A where thanhtoan.id=A.thanhtoan_id ORDER BY thanhtoan.id DESC",nativeQuery = true)
	List<ThanhToan> findbyUserId(Long id);
	
	// check hoa da duoc luu chua, neu da dc luu thi chi lay 1 ban ghi
	@Query(value = "select * from thanhtoan where magiaodich=?1 and sohoadon=?2 limit 1", nativeQuery =  true)
	ThanhToan checkExist(String vnp_TransactionNo,String vnp_TxnRef);
	
}
