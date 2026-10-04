package com.manacommunity.api.food.repository;

import com.manacommunity.api.food.model.DiningEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningEventRepository extends JpaRepository<DiningEvent, Long> {
    Page<DiningEvent> findByCommunityIdAndStatusInOrderByEventDateAsc(Long communityId, List<String> statuses, Pageable pageable);
    Page<DiningEvent> findByCommunityIdOrderByEventDateDesc(Long communityId, Pageable pageable);
}
