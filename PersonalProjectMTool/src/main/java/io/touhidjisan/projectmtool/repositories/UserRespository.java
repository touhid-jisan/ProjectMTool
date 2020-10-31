package io.touhidjisan.projectmtool.repositories;

import io.touhidjisan.projectmtool.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends CrudRepository<User, Long> {
}
