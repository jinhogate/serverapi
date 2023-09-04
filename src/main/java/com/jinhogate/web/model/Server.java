package com.jinhogate.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicUpdate;

import com.jinhogate.web.model.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Server {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, length = 20)
	@NotEmpty(message = "Ip address cannot be empty or null")
	private String ipAddress;
	private String name;
	private String memmory;
	private String type;
	private String imageUrl;
	private StatusEnum status;

}
