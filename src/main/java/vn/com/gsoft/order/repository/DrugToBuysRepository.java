package vn.com.gsoft.order.repository;

import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.com.gsoft.order.entity.DrugToBuys;
import vn.com.gsoft.order.model.dto.DrugToBuysReq;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugToBuysRepository extends BaseRepository<DrugToBuys, DrugToBuysReq, Long> {
  @Query("SELECT c FROM DrugToBuys c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.storeCode} IS NULL OR lower(c.storeCode) LIKE lower(concat('%',CONCAT(:#{#param.storeCode},'%'))))"
          + " AND (:#{#param.drugId} IS NULL OR c.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.quantity} IS NULL OR c.quantity = :#{#param.quantity}) "
          + " AND (:#{#param.unitId} IS NULL OR c.unitId = :#{#param.unitId}) "
          + " AND (:#{#param.staffUserId} IS NULL OR c.staffUserId = :#{#param.staffUserId}) "
          + " AND (:#{#param.statusId} IS NULL OR c.statusId = :#{#param.statusId}) "
          + " AND (:#{#param.receiptNoteId} IS NULL OR c.receiptNoteId = :#{#param.receiptNoteId}) "
          + " AND (:#{#param.inPrice} IS NULL OR c.inPrice = :#{#param.inPrice}) "
          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " ORDER BY c.id desc"
  )
  Page<DrugToBuys> searchPage(@Param("param") DrugToBuysReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM DrugToBuys c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.storeCode} IS NULL OR lower(c.storeCode) LIKE lower(concat('%',CONCAT(:#{#param.storeCode},'%'))))"
          + " AND (:#{#param.drugId} IS NULL OR c.drugId = :#{#param.drugId}) "
          + " AND (:#{#param.quantity} IS NULL OR c.quantity = :#{#param.quantity}) "
          + " AND (:#{#param.unitId} IS NULL OR c.unitId = :#{#param.unitId}) "
          + " AND (:#{#param.created} IS NULL OR c.created >= :#{#param.createdFrom}) "
          + " AND (:#{#param.created} IS NULL OR c.created <= :#{#param.createdTo}) "
          + " AND (:#{#param.completeDate} IS NULL OR c.completeDate >= :#{#param.completeDateFrom}) "
          + " AND (:#{#param.completeDate} IS NULL OR c.completeDate <= :#{#param.completeDateTo}) "
          + " AND (:#{#param.staffUserId} IS NULL OR c.staffUserId = :#{#param.staffUserId}) "
          + " AND (:#{#param.statusId} IS NULL OR c.statusId = :#{#param.statusId}) "
          + " AND (:#{#param.receiptNoteId} IS NULL OR c.receiptNoteId = :#{#param.receiptNoteId}) "
          + " AND (:#{#param.inPrice} IS NULL OR c.inPrice = :#{#param.inPrice}) "
          + " AND (:#{#param.description} IS NULL OR lower(c.description) LIKE lower(concat('%',CONCAT(:#{#param.description},'%'))))"
          + " AND (:#{#param.archivedId} IS NULL OR c.archivedId = :#{#param.archivedId}) "
          + " ORDER BY c.id desc"
  )
  List<DrugToBuys> searchList(@Param("param") DrugToBuysReq param);

  Optional<DrugToBuys> findByIdAndStatusId(Long id,Long statusId);

}
