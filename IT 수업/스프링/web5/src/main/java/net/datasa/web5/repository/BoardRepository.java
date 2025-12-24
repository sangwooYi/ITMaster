package net.datasa.web5.repository;

import net.datasa.web5.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    Page<BoardEntity> findByTitleContaining(String title, Pageable pageable);

    Page<BoardEntity> findByContentContaining(String content, Pageable pageable);
}
