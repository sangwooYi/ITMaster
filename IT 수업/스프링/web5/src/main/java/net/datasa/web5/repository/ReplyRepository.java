package net.datasa.web5.repository;

import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {

    List<ReplyEntity> findAllByBoardNum(Integer boardNum);

    Integer board(BoardEntity board);
}
