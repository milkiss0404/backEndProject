package com.study.project.zone;

import com.study.project.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByCityAndProvince(String cityName, String provinceName);
}
