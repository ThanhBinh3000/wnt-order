package vn.com.gsoft.order.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.OrderDetails;
import vn.com.gsoft.order.entity.PhieuXuatChiTiets;
import vn.com.gsoft.order.model.dto.OrderDetailsReq;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;

import java.util.List;

@Repository
public interface PhieuXuatChiTietsRepository extends CrudRepository<PhieuXuatChiTiets, Long> {

    List<PhieuXuatChiTiets> findAllByPhieuXuatMaPhieuXuatAndRecordStatusId(Long phieuXuatMaPhieuXuat,Long recordStatusId);


    @Query("SELECT c FROM PhieuXuatChiTiets c " +
            " JOIN PhieuXuats px on c.phieuXuatMaPhieuXuat = px.id" +
            " WHERE 1=1 "
            + " AND (:#{#param.pickUpOrderId} IS NULL OR px.pickUpOrderId = :#{#param.pickUpOrderId}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR px.recordStatusId = :#{#param.recordStatusId}) "
            + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
            + " ORDER BY c.id desc"
    )
    List<PhieuXuatChiTiets> searchListByPickUpOrder(@Param("param") PickUpOrderDetailReq param);
}
