package io.ramzi.elevator.simulator.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.ramzi.elevator.simulator.dao.entity.UserRequestEntity;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequestEntity, Long> {

}
