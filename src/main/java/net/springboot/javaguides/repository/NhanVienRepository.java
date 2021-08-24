package net.springboot.javaguides.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.springboot.javaguides.entity.NhanVien;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long>{
	//tim nhan vien theo doanh nghiep id
	@Query(value = "select * from nhanvien where doanhnghiep_id=?1", nativeQuery = true)
	List<NhanVien> findAllByDoanhNghiepId(Long id);
	
	//tim nhan vien theo ma BHXH
	@Query(value = "select * from nhanvien where maBHXH=?1",nativeQuery = true)
	NhanVien findByMaBHXH(String maBHXH);
}
