package vn.com.gsoft.order.service;

import vn.com.gsoft.order.model.system.Profile;

public interface BaseService {
    Profile getLoggedUser() throws Exception;

}
