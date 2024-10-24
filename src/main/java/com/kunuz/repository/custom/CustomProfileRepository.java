package com.kunuz.repository.custom;

import com.kunuz.dto.FilterDto;
import com.kunuz.dto.ProfileDto;
import com.kunuz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomProfileRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterDto<ProfileEntity> filter(ProfileDto profileDto) {
        StringBuilder generalBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (profileDto.getName() != null) {
            generalBuilder.append(" and lower(p.name) like :name ");
            params.put("name", "%" + profileDto.getName().toLowerCase() + "%");
        }
        if (profileDto.getSurname() != null) {
            generalBuilder.append(" and lower(p.surname) like :surname");
            params.put("surname", "%" + profileDto.getSurname().toLowerCase() + "%");
        }
        if (profileDto.getPhone() != null) {
            generalBuilder.append(" and p.phone=:phone");
            params.put("phone", profileDto.getPhone());
        }
/*        if (profileDto.getCreatedDate() != null) {
            generalBuilder.append(" and p.createdDate >=:from ");
            params.put("from", profileDto.getCreatedDate());
        }
        if (profileDto.getCreatedDate() != null) {
            generalBuilder.append("and p.createdDate <=:to ");
            params.put("to", profileDto.getCreatedDate());
        }*/
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("select p.* from profiles as p where 1=1 ");
        selectQuery.append(generalBuilder);

        Query query = entityManager.createNativeQuery(selectQuery.toString(), ProfileEntity.class);

        for (Map.Entry<String, Object> objectEntry : params.entrySet()) {
            query.setParameter(objectEntry.getKey(), objectEntry.getValue());
        }

        List<ProfileEntity> entityList = query.getResultList();

        return new FilterDto<>(entityList);
    }
}
