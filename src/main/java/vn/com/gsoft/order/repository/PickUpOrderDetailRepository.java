package vn.com.gsoft.order.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;

import java.util.List;

@Repository
public interface PickUpOrderDetailRepository extends BaseRepository<PickUpOrderDetail, PickUpOrderDetailReq, Long> {
  @Query("SELECT c FROM PickUpOrderDetail c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.orderId} IS NULL OR c.orderId = :#{#param.orderId}) "
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.drugId} IS NULL OR c.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.unitId} IS NULL OR c.unitId = :#{#param.unitId}) "
          + " AND (:#{#param.price} IS NULL OR c.price = :#{#param.price}) "
          + " AND (:#{#param.inPrice} IS NULL OR c.inPrice = :#{#param.inPrice}) "
          + " AND (:#{#param.quantity} IS NULL OR c.quantity = :#{#param.quantity}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.preRetailQuantity} IS NULL OR c.preRetailQuantity = :#{#param.preRetailQuantity}) "
          + " AND (:#{#param.drugToBuyId} IS NULL OR c.drugToBuyId = :#{#param.drugToBuyId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " ORDER BY c.id desc"
  )
  Page<PickUpOrderDetail> searchPage(@Param("param") PickUpOrderDetailReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM PickUpOrderDetail c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.orderId} IS NULL OR c.orderId = :#{#param.orderId}) "
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.drugId} IS NULL OR c.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.unitId} IS NULL OR c.unitId = :#{#param.unitId}) "
          + " AND (:#{#param.price} IS NULL OR c.price = :#{#param.price}) "
          + " AND (:#{#param.inPrice} IS NULL OR c.inPrice = :#{#param.inPrice}) "
          + " AND (:#{#param.quantity} IS NULL OR c.quantity = :#{#param.quantity}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.preRetailQuantity} IS NULL OR c.preRetailQuantity = :#{#param.preRetailQuantity}) "
          + " AND (:#{#param.drugToBuyId} IS NULL OR c.drugToBuyId = :#{#param.drugToBuyId}) "
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " AND (:#{#param.referenceId} IS NULL OR c.referenceId = :#{#param.referenceId}) "
          + " ORDER BY c.id desc"
  )
  List<PickUpOrderDetail> searchList(@Param("param") PickUpOrderDetailReq param);

}
