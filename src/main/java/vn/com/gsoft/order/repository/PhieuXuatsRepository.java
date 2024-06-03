package vn.com.gsoft.order.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.PhieuXuats;

import java.util.Optional;


@Repository
public interface PhieuXuatsRepository extends CrudRepository<PhieuXuats, Long> {


    Optional<PhieuXuats> findByPickUpOrderIdAndRecordStatusId(Long pickUpOrderId,Long recordStatusId);


}
