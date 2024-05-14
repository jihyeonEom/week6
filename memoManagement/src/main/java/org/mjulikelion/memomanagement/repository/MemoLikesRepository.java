package org.mjulikelion.memomanagement.repository;

import org.mjulikelion.memomanagement.model.MemoLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemoLikesRepository extends JpaRepository<MemoLikes, UUID> {
}
