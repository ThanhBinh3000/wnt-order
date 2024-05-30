package vn.com.gsoft.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.DonViTinhs;

@Repository
public interface DonViTinhsRepository extends CrudRepository<DonViTinhs, Long> {


}
