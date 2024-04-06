package vn.com.gsoft.order.service;

import vn.com.gsoft.order.model.system.Profile;

import java.util.Optional;

public interface UserService {
    Optional<Profile> findUserByToken(String token);
}
