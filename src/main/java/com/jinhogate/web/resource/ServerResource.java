package com.jinhogate.web.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinhogate.web.model.Response;
import com.jinhogate.web.model.Server;
import com.jinhogate.web.model.enums.StatusEnum;
import com.jinhogate.web.service.implementation.ServerServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {

	private final ServerServiceImpl serverService;

	@GetMapping("/list")
	public ResponseEntity<Response> getServers() throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("servers", this.serverService.list(30)))
						.message("Server retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
		Server server = this.serverService.ping(ipAddress);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("server", server))
						.message(server.getStatus() == StatusEnum.SERVER_UP ? "Ping success" : "Ping failed")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) throws InterruptedException {
		TimeUnit.SECONDS.sleep(3);
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("server", this.serverService.create(server)))
						.message("Server created")
						.status(HttpStatus.CREATED)
						.statusCode(HttpStatus.CREATED.value())
						.build());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("server", this.serverService.get(id)))
						.message("Server retrieved")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(
				Response.builder()
						.timeStamp(LocalDateTime.now())
						.data(Map.of("deleted", this.serverService.delete(id)))
						.message("Server deleted")
						.status(HttpStatus.OK)
						.statusCode(HttpStatus.OK.value())
						.build());
	}

	@GetMapping(path = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
		return Files
				.readAllBytes(Paths.get(System.getProperty("user.home") + "\\Downloads\\images\\" + fileName));
	}

}
