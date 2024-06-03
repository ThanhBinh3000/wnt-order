package vn.com.gsoft.order.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.PhieuNhapChiTiets;
import vn.com.gsoft.order.entity.PhieuXuatChiTiets;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;


import java.util.List;

@Repository
public interface  PhieuNhapChiTietsRepository extends CrudRepository<PhieuNhapChiTiets, Long> {

  List<PhieuNhapChiTiets> findAllByPhieuNhapMaPhieuNhapAndRecordStatusIdAndThuocThuocId(Long maPhieuNhap,Long recordStatusIdm,Long thuocId);
  List<PhieuNhapChiTiets> findAllByPhieuNhapMaPhieuNhapAndRecordStatusId(Long maPhieuNhap,Long recordStatusIdm);


  @Query("SELECT c FROM PhieuNhapChiTiets c " +
          " JOIN PhieuNhaps pn on c.phieuNhapMaPhieuNhap = pn.id" +
          " WHERE 1=1 "
          + " AND (:#{#param.phieuNhapId} IS NULL OR pn.id = :#{#param.phieuNhapId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR pn.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " ORDER BY c.id desc"
  )
  List<PhieuNhapChiTiets> searchListByPhieuNhapId(@Param("param") PickUpOrderDetailReq param);
}
