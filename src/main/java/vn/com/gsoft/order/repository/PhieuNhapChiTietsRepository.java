package vn.com.gsoft.order.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.PhieuNhapChiTiets;


import java.util.List;

@Repository
public interface  PhieuNhapChiTietsRepository extends CrudRepository<PhieuNhapChiTiets, Long> {

  List<PhieuNhapChiTiets> findAllByPhieuNhapMaPhieuNhapAndRecordStatusIdAndThuocThuocId(Long maPhieuNhap,Long recordStatusIdm,Long thuocId);
  List<PhieuNhapChiTiets> findAllByPhieuNhapMaPhieuNhapAndRecordStatusId(Long maPhieuNhap,Long recordStatusIdm);
}
