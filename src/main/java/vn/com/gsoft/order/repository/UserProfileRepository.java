package vn.com.gsoft.order.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.order.entity.PickUpOrder;
import vn.com.gsoft.order.entity.UserProfile;
import vn.com.gsoft.order.model.dto.PickUpOrderReq;
import vn.com.gsoft.order.model.dto.UserProfileReq;

import java.util.List;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {


    @Query("SELECT c FROM UserProfile c " +
            "WHERE 1=1 "
            + " AND (:#{#param.id} IS NULL OR c.id = :#{#param.id}) "
            + " AND (:#{#param.maNhaThuoc} IS NULL OR c.maNhaThuoc = :#{#param.maNhaThuoc}) "
            + " AND (:#{#param.hoatDong} IS NULL OR c.hoatDong = :#{#param.hoatDong}) "
            + " ORDER BY c.id desc"
    )
    List<UserProfile> searchList(@Param("param") UserProfileReq param);
}
