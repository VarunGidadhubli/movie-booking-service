package com.movie.booking.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "theatres")
public class Theatre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String address;

	private String city;

	private String pincode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TheatreStatus status;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdByUser;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private User approvedByUser;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

    @OneToMany(mappedBy = "theatre")
    private List<Screen> screens = new ArrayList<>();
	
	public Theatre() {

	}

	public Theatre(Long id, String name, String address, String city, String pincode, TheatreStatus status,
			User createdByUser, User approvedByUser, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.pincode = pincode;
		this.status = status;
		this.createdByUser = createdByUser;
		this.approvedByUser = approvedByUser;
		this.createdAt = createdAt;
	}

	public TheatreStatus getStatus() {
		return status;
	}

	public void setStatus(TheatreStatus status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public User getApprovedByUser() {
		return approvedByUser;
	}

	public void setApprovedByUser(User approvedByUser) {
		this.approvedByUser = approvedByUser;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Screen> getScreens() {
		return screens;
	}

	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}
}