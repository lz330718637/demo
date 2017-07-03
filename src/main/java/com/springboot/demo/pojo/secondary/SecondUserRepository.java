package com.springboot.demo.pojo.secondary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.pojo.User;

@Repository
public interface SecondUserRepository extends JpaRepository<SecondUser, Long> {
	List<SecondUser> findByName(String name);
}
