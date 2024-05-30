package vn.com.gsoft.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.OrderStatus;
import vn.com.gsoft.order.entity.OrderStatusId;
import vn.com.gsoft.order.entity.Orders;
import vn.com.gsoft.order.model.dto.OrdersReq;
import vn.com.gsoft.order.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatusRepository extends BaseRepository<OrderStatus, OrdersReq, Long> {
  @Query("SELECT c FROM OrderStatus c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.name} IS NULL OR c.name = :#{#param.name}) "
          + " ORDER BY c.id desc"
  )
  Page<OrderStatus> searchPage(@Param("param") OrdersReq param, Pageable pageable);
  
  
  @Query("SELECT c FROM OrderStatus c " +
         "WHERE 1=1 "
          + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
          + " AND (:#{#param.name} IS NULL OR c.name = :#{#param.name}) "
          + " ORDER BY c.id desc"
  )
  List<OrderStatus> searchList(@Param("param") OrdersReq param);

}
