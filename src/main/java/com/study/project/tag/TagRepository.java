package com.study.project.tag;

import com.study.project.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTitle(String title);
}
