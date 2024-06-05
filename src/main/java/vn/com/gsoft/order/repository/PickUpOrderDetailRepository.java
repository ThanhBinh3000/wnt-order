package vn.com.gsoft.order.repository;

import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;

import java.util.List;
import java.util.Optional;

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


  List<PickUpOrderDetail> findAllByOrderIdAndRecordStatusId(Long orderId,Long recordStatusId);
  List<PickUpOrderDetail> findAllByOrderId(Long orderId);


  @Query(value = "SELECT distinct c.OrderId AS orderId, n.Staff_UserId AS staffUserId"
          + " FROM PickUpOrderDetail c"
          + " JOIN DrugToBuys n ON c.DrugToBuy_Id = n.id"
          + " WHERE n.Staff_UserId > 0"
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.ids.size() }  = 0 OR c.id IN (:#{#param.ids})) ",
          nativeQuery = true
  )
  Optional<Tuple> searchStaffAssign(@Param("param") PickUpOrderDetailReq param);


  @Query("SELECT ni FROM PickUpOrderDetail ni " +
          " JOIN PickUpOrder n on n.id = ni.orderId " +
          " LEFT JOIN DrugToBuys dr on dr.id = ni.drugToBuyId " +
          " WHERE ni.quantity > 0 "
          + " AND (:#{#param.id} IS NULL OR ni.id = :#{#param.id}) "
          + " AND (:#{#param.orderId} IS NULL OR ni.orderId = :#{#param.orderId}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR n.drugStoreId = :#{#param.drugStoreId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR ni.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR n.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.itemOrder} IS NULL OR ni.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.drugId} IS NULL OR ni.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.orderStatusIds.size() }  = 0 OR n.orderStatusId IN (:#{#param.orderStatusIds})) "
          + " AND (:#{#param.fromDate} IS NULL OR n.created >= :#{#param.fromDate}) "
          + " AND (:#{#param.toDate} IS NULL OR n.created <= :#{#param.toDate}) "
          + " AND (:#{#param.orderNumber} IS NULL OR n.orderNumber = :#{#param.orderNumber}) "
          + " AND (:#{#param.staffUserId} IS NULL OR dr.staffUserId = :#{#param.staffUserId }) "
          + " AND (:#{#param.statusId} IS NULL OR dr.statusId = :#{#param.statusId}) "
          + " ORDER BY n.orderDate desc"
  )
  Page<PickUpOrderDetail> searchPageCustom(@Param("param") PickUpOrderDetailReq param, Pageable pageable);

  @Query("SELECT ni FROM PickUpOrderDetail ni " +
          " JOIN PickUpOrder n on n.id = ni.orderId " +
          " JOIN Thuocs thuoc on thuoc.id = ni.drugId " +
          " LEFT JOIN DrugToBuys dr on dr.id = ni.drugToBuyId " +
          " WHERE ni.quantity > 0 "
          + " AND (:#{#param.id} IS NULL OR ni.id = :#{#param.id}) "
          + " AND (:#{#param.orderId} IS NULL OR ni.orderId = :#{#param.orderId}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR n.drugStoreId = :#{#param.drugStoreId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR ni.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR n.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.itemOrder} IS NULL OR ni.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.drugId} IS NULL OR ni.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.orderStatusIds.size() }  = 0 OR n.orderStatusId IN (:#{#param.orderStatusIds})) "
          + " AND (:#{#param.fromDate} IS NULL OR n.updated >= :#{#param.fromDate}) "
          + " AND (:#{#param.toDate} IS NULL OR n.updated <= :#{#param.toDate}) "
          + " AND (:#{#param.orderNumber} IS NULL OR n.orderNumber = :#{#param.orderNumber}) "
          + " AND (:#{#param.staffUserId} IS NULL OR dr.staffUserId = :#{#param.staffUserId }) "
          + " AND (:#{#param.statusId} IS NULL OR dr.statusId = :#{#param.statusId}) "
          + " ORDER BY n.orderDate desc"
  )
  Page<PickUpOrderDetail> searchPageDeliveryConfirm(@Param("param") PickUpOrderDetailReq param, Pageable pageable);

  Optional<PickUpOrderDetail> findByDrugToBuyId(Long drugToBuyId);

  void deleteAllByOrderId(Long orderId);

}
