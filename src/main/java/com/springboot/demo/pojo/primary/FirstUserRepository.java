package com.springboot.demo.pojo.primary;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@CacheConfig(cacheNames= "firstUsers")
@Repository
public interface FirstUserRepository extends JpaRepository<FirstUser, Long> {
	@Cacheable
	List<FirstUser> findByName(String name);
}
