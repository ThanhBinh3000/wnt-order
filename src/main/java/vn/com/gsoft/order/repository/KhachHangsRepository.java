package vn.com.gsoft.order.repository;

import jakarta.persistence.Tuple;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.KhachHangs;

import java.util.List;

@Repository
public interface KhachHangsRepository extends CrudRepository<KhachHangs, Long> {


}
