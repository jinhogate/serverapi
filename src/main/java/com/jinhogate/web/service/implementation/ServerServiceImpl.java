package com.jinhogate.web.service.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jinhogate.web.model.Server;
import com.jinhogate.web.model.enums.StatusEnum;
import com.jinhogate.web.repo.ServerRepo;
import com.jinhogate.web.service.ServerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

	private final ServerRepo serverRepo;

	@Override
	public Server create(Server server) {
		ServerServiceImpl.log.info("Create new Server {}", server);
		server.setImageUrl(this.setServerImageUrl());
		return this.serverRepo.save(server);
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		ServerServiceImpl.log.info("Pinging server IP :{}", ipAddress);
		Server server = this.serverRepo.findByIpAddress(ipAddress);
		InetAddress address = InetAddress.getByName(ipAddress);
		server.setStatus(address.isReachable(10000) ? StatusEnum.SERVER_UP : StatusEnum.SERVER_DOWN);
		return this.serverRepo.save(server);
	}

	@Override
	public Collection<Server> list(int limit) {
		ServerServiceImpl.log.info("Fetch all servers");
		return this.serverRepo.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		ServerServiceImpl.log.info("Fetch server by id {}", id);
		return this.serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		ServerServiceImpl.log.info("Update server {}", server);
		return this.serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		ServerServiceImpl.log.info("Delete server by id {}", id);
		this.serverRepo.deleteById(id);
		return true;
	}

	private String setServerImageUrl() {
		String[] imageNames = { "server1.png", "server2.png", "server3.png", "server4.png" };
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
	}

}
