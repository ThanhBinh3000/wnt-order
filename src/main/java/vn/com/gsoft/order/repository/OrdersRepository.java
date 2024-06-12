package vn.com.gsoft.order.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.order.entity.Orders;
import vn.com.gsoft.order.model.dto.OrdersReq;

import java.util.List;

@Repository
public interface OrdersRepository extends BaseRepository<Orders, OrdersReq, Long> {
  @Query("SELECT distinct c FROM Orders c " +
         "LEFT JOIN OrderDetails dtl ON dtl.orderId = c.id " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.invoiceId} IS NULL OR c.invoiceId = :#{#param.invoiceId}) "
          + " AND (:#{#param.secureCode} IS NULL OR lower(c.secureCode) LIKE lower(concat('%',CONCAT(:#{#param.secureCode},'%'))))"
          + " AND (:#{#param.totalAmount} IS NULL OR c.totalAmount = :#{#param.totalAmount}) "
          + " AND (:#{#param.totalPriceWithoutPromotion} IS NULL OR c.totalPriceWithoutPromotion = :#{#param.totalPriceWithoutPromotion}) "
          + " AND (:#{#param.vat} IS NULL OR c.vat = :#{#param.vat}) "
          + " AND (:#{#param.promotionId} IS NULL OR c.promotionId = :#{#param.promotionId}) "
          + " AND (:#{#param.paymentMethodId} IS NULL OR c.paymentMethodId = :#{#param.paymentMethodId}) "
          + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
          + " AND (:#{#param.userId} IS NULL OR c.userId = :#{#param.userId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.orderNumber} IS NULL OR c.orderNumber = :#{#param.orderNumber}) "
          + " AND (:#{#param.customerId} IS NULL OR c.createdByUserId = :#{#param.customerId}) "
          + " AND (:#{#param.orderStatusId} IS NULL OR c.orderStatusId = :#{#param.orderStatusId}) "
          + " AND (:#{#param.drugId} IS NULL OR dtl.drugId = :#{#param.drugId}) "
//          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
//          + " AND (:#{#param.supplierDrugStoreId} IS NULL OR lower(c.supplierDrugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.supplierDrugStoreId},'%'))))"
//          + " AND (:#{#param.orderStatusId} IS NULL OR c.orderStatusId = :#{#param.orderStatusId}) "
//          + " AND (:#{#param.paymentAmount} IS NULL OR c.paymentAmount = :#{#param.paymentAmount}) "
//          + " AND (:#{#param.supplierDescription} IS NULL OR lower(c.supplierDescription) LIKE lower(concat('%',CONCAT(:#{#param.supplierDescription},'%'))))"
//          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
//          + " AND (:#{#param.orderNumber} IS NULL OR c.orderNumber = :#{#param.orderNumber}) "
//          + " AND (:#{#param.noteId} IS NULL OR c.noteId = :#{#param.noteId}) "
//          + " AND (:#{#param.deliveryNoteId} IS NULL OR c.deliveryNoteId = :#{#param.deliveryNoteId}) "
//          + " AND (:#{#param.receiptNoteId} IS NULL OR c.receiptNoteId = :#{#param.receiptNoteId}) "
//          + " AND (:#{#param.targetCombinedOrderId} IS NULL OR c.targetCombinedOrderId = :#{#param.targetCombinedOrderId}) "
//          + " AND (:#{#param.sourceCombinedOrderIds} IS NULL OR lower(c.sourceCombinedOrderIds) LIKE lower(concat('%',CONCAT(:#{#param.sourceCombinedOrderIds},'%'))))"
//          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
//          + " AND (:#{#param.supplierStoreId} IS NULL OR c.supplierStoreId = :#{#param.supplierStoreId}) "
//          + " AND (:#{#param.noteTypeId} IS NULL OR c.noteTypeId = :#{#param.noteTypeId}) "
//          + " AND (:#{#param.customerId} IS NULL OR c.customerId = :#{#param.customerId}) "
//          + " AND (:#{#param.customerAddress} IS NULL OR lower(c.customerAddress) LIKE lower(concat('%',CONCAT(:#{#param.customerAddress},'%'))))"
//          + " AND (:#{#param.customerFullName} IS NULL OR lower(c.customerFullName) LIKE lower(concat('%',CONCAT(:#{#param.customerFullName},'%'))))"
//          + " AND (:#{#param.customerPhone} IS NULL OR lower(c.customerPhone) LIKE lower(concat('%',CONCAT(:#{#param.customerPhone},'%'))))"
//          + " AND (:#{#param.customerCode} IS NULL OR lower(c.customerCode) LIKE lower(concat('%',CONCAT(:#{#param.customerCode},'%'))))"
          + " ORDER BY c.id desc"
  )
  Page<Orders> searchPage(@Param("param") OrdersReq param, Pageable pageable);


  @Query("SELECT c FROM Orders c " +
          "LEFT JOIN OrderDetails dtl ON dtl.orderId = c.id " +
          "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.invoiceId} IS NULL OR c.invoiceId = :#{#param.invoiceId}) "
          + " AND (:#{#param.secureCode} IS NULL OR lower(c.secureCode) LIKE lower(concat('%',CONCAT(:#{#param.secureCode},'%'))))"
          + " AND (:#{#param.totalAmount} IS NULL OR c.totalAmount = :#{#param.totalAmount}) "
          + " AND (:#{#param.totalPriceWithoutPromotion} IS NULL OR c.totalPriceWithoutPromotion = :#{#param.totalPriceWithoutPromotion}) "
          + " AND (:#{#param.vat} IS NULL OR c.vat = :#{#param.vat}) "
          + " AND (:#{#param.promotionId} IS NULL OR c.promotionId = :#{#param.promotionId}) "
          + " AND (:#{#param.paymentMethodId} IS NULL OR c.paymentMethodId = :#{#param.paymentMethodId}) "
          + " AND (:#{#param.discount} IS NULL OR c.discount = :#{#param.discount}) "
          + " AND (:#{#param.userId} IS NULL OR c.userId = :#{#param.userId}) "
          + " AND (:#{#param.recordStatusId} IS NULL OR c.recordStatusId = :#{#param.recordStatusId}) "
          + " AND (:#{#param.orderNumber} IS NULL OR c.orderNumber = :#{#param.orderNumber}) "
          + " AND (:#{#param.customerId} IS NULL OR c.createdByUserId = :#{#param.customerId}) "
          + " AND (:#{#param.orderStatusId} IS NULL OR c.orderStatusId = :#{#param.orderStatusId}) "
          + " AND (:#{#param.drugId} IS NULL OR dtl.drugId = :#{#param.drugId}) "
//          + " AND (:#{#param.drugStoreId} IS NULL OR lower(c.drugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.drugStoreId},'%'))))"
//          + " AND (:#{#param.supplierDrugStoreId} IS NULL OR lower(c.supplierDrugStoreId) LIKE lower(concat('%',CONCAT(:#{#param.supplierDrugStoreId},'%'))))"
//          + " AND (:#{#param.orderStatusId} IS NULL OR c.orderStatusId = :#{#param.orderStatusId}) "
//          + " AND (:#{#param.paymentAmount} IS NULL OR c.paymentAmount = :#{#param.paymentAmount}) "
//          + " AND (:#{#param.supplierDescription} IS NULL OR lower(c.supplierDescription) LIKE lower(concat('%',CONCAT(:#{#param.supplierDescription},'%'))))"
//          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
//          + " AND (:#{#param.orderNumber} IS NULL OR c.orderNumber = :#{#param.orderNumber}) "
//          + " AND (:#{#param.noteId} IS NULL OR c.noteId = :#{#param.noteId}) "
//          + " AND (:#{#param.deliveryNoteId} IS NULL OR c.deliveryNoteId = :#{#param.deliveryNoteId}) "
//          + " AND (:#{#param.receiptNoteId} IS NULL OR c.receiptNoteId = :#{#param.receiptNoteId}) "
//          + " AND (:#{#param.targetCombinedOrderId} IS NULL OR c.targetCombinedOrderId = :#{#param.targetCombinedOrderId}) "
//          + " AND (:#{#param.sourceCombinedOrderIds} IS NULL OR lower(c.sourceCombinedOrderIds) LIKE lower(concat('%',CONCAT(:#{#param.sourceCombinedOrderIds},'%'))))"
//          + " AND (:#{#param.storeId} IS NULL OR c.storeId = :#{#param.storeId}) "
//          + " AND (:#{#param.supplierStoreId} IS NULL OR c.supplierStoreId = :#{#param.supplierStoreId}) "
//          + " AND (:#{#param.noteTypeId} IS NULL OR c.noteTypeId = :#{#param.noteTypeId}) "
//          + " AND (:#{#param.customerId} IS NULL OR c.customerId = :#{#param.customerId}) "
//          + " AND (:#{#param.customerAddress} IS NULL OR lower(c.customerAddress) LIKE lower(concat('%',CONCAT(:#{#param.customerAddress},'%'))))"
//          + " AND (:#{#param.customerFullName} IS NULL OR lower(c.customerFullName) LIKE lower(concat('%',CONCAT(:#{#param.customerFullName},'%'))))"
//          + " AND (:#{#param.customerPhone} IS NULL OR lower(c.customerPhone) LIKE lower(concat('%',CONCAT(:#{#param.customerPhone},'%'))))"
//          + " AND (:#{#param.customerCode} IS NULL OR lower(c.customerCode) LIKE lower(concat('%',CONCAT(:#{#param.customerCode},'%'))))"
          + " ORDER BY c.id desc"
  )
  List<Orders> searchList(@Param("param") OrdersReq param);
  @Query(value = "SELECT top 1 c.* from Orders c order by c.id desc ", nativeQuery = true)
  Orders findIdMax();


}
