package org.mjulikelion.memomanagement.repository;

import org.mjulikelion.memomanagement.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemoRepository extends JpaRepository<Memo, UUID> {
}
