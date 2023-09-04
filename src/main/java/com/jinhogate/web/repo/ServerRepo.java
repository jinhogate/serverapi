package com.jinhogate.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jinhogate.web.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {

	Server findByIpAddress(String ipAddress);

}
