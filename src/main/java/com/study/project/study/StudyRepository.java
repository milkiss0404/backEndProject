package com.study.project.study;

import com.study.project.domain.Study;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface StudyRepository extends JpaRepository<Study,Long> {
    boolean existsByPath(String path);

    @EntityGraph(value = "Study.withAll",type = EntityGraph.EntityGraphType.LOAD)
    //
    Study findByPath(String path);
}
