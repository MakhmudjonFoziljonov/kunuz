package com.kunuz.repository;

import com.kunuz.dto.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long> {
}
