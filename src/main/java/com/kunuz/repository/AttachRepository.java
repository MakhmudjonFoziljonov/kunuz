package com.kunuz.repository;

import com.kunuz.entity.AttachEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity, String> {


}
