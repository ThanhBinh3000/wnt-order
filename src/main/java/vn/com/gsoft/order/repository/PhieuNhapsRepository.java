package vn.com.gsoft.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.PhieuNhaps;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuNhapsRepository extends CrudRepository<PhieuNhaps, Long> {

    Optional<PhieuNhaps> findByPickUpOrderId(Long pickUpOrderId);

}
