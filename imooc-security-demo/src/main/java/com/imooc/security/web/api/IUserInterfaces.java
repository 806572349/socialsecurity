package com.imooc.security.web.api;

import com.imooc.security.web.model.LaosijiUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IUserInterfaces extends MongoRepository<LaosijiUser,String> {
    List<LaosijiUser> findByUsername(String username);
}
