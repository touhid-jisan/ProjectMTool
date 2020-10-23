package io.touhidjisan.projectmtool.repositories;

import io.touhidjisan.projectmtool.model.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
}
