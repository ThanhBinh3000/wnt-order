package vn.com.gsoft.order.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.order.entity.OrderDetails;
import vn.com.gsoft.order.model.dto.OrderDetailsReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends BaseRepository<OrderDetails, OrderDetailsReq, Long> {
  @Query("SELECT c FROM OrderDetails c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.orderId} IS NULL OR c.orderId = :#{#param.orderId}) "
          + " AND (:#{#param.drugId} IS NULL OR c.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.unitId} IS NULL OR c.unitId = :#{#param.unitId}) "
          + " AND (:#{#param.retailUnitId} IS NULL OR c.retailUnitId = :#{#param.retailUnitId}) "
          + " AND (:#{#param.quantity} IS NULL OR c.quantity = :#{#param.quantity}) "
          + " AND (:#{#param.price} IS NULL OR c.price = :#{#param.price}) "
          + " AND (:#{#param.factors} IS NULL OR c.factors = :#{#param.factors}) "
          + " AND (:#{#param.realQuantity} IS NULL OR c.realQuantity = :#{#param.realQuantity}) "
          + " AND (:#{#param.totalAmount} IS NULL OR c.totalAmount = :#{#param.totalAmount}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
          + " AND (:#{#param.createdByStoreId} IS NULL OR c.createdByStoreId = :#{#param.createdByStoreId}) "
          + " AND (:#{#param.supplierStoreCode} IS NULL OR lower(c.supplierStoreCode) LIKE lower(concat('%',CONCAT(:#{#param.supplierStoreCode},'%'))))"
          + " AND (:#{#param.inPrice} IS NULL OR c.inPrice = :#{#param.inPrice}) "
          + " ORDER BY c.id desc"
  )
  Page<OrderDetails> searchPage(@Param("param") OrderDetailsReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM OrderDetails c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.orderId} IS NULL OR c.orderId = :#{#param.orderId}) "
          + " AND (:#{#param.drugId} IS NULL OR c.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.unitId} IS NULL OR c.unitId = :#{#param.unitId}) "
          + " AND (:#{#param.retailUnitId} IS NULL OR c.retailUnitId = :#{#param.retailUnitId}) "
          + " AND (:#{#param.quantity} IS NULL OR c.quantity = :#{#param.quantity}) "
          + " AND (:#{#param.price} IS NULL OR c.price = :#{#param.price}) "
          + " AND (:#{#param.factors} IS NULL OR c.factors = :#{#param.factors}) "
          + " AND (:#{#param.realQuantity} IS NULL OR c.realQuantity = :#{#param.realQuantity}) "
          + " AND (:#{#param.totalAmount} IS NULL OR c.totalAmount = :#{#param.totalAmount}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
          + " AND (:#{#param.itemOrder} IS NULL OR c.itemOrder = :#{#param.itemOrder}) "
          + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
          + " AND (:#{#param.createdByStoreId} IS NULL OR c.createdByStoreId = :#{#param.createdByStoreId}) "
          + " AND (:#{#param.supplierStoreCode} IS NULL OR lower(c.supplierStoreCode) LIKE lower(concat('%',CONCAT(:#{#param.supplierStoreCode},'%'))))"
          + " AND (:#{#param.inPrice} IS NULL OR c.inPrice = :#{#param.inPrice}) "
          + " ORDER BY c.id desc"
  )
  List<OrderDetails> searchList(@Param("param") OrderDetailsReq param);

  void deleteAllByOrderId(Long idHdr);

  List<OrderDetails> findAllByOrderId(Long id);

}
