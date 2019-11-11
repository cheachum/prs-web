package com.prs.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Request {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id; 
	private int userId;
	@ManyToOne
	@JoinColumn(name="UserID")
	private User user;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode; 
	private String status;
	private double total;
	private String reasonForRejection;
	private LocalDateTime submittedDate;
	public Request() {
		super();
	}
	public Request(int id, int userId, User user, String description, String justification, LocalDate dateNeeded,
			String deliveryMode, String status, double total, String reasonForRejection, LocalDateTime submittedDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.reasonForRejection = reasonForRejection;
		this.submittedDate = submittedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public LocalDate getDateNeeded() {
		return dateNeeded;
	}
	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public LocalDate getSubmittedDate() {
		return getSubmittedDate();
	}
	public void setSubmittedDate(LocalDateTime localDateTime) {
		this.submittedDate = localDateTime;
	}
	@Override
	public String toString() {
		return "Request [id=" + id + ", userId=" + userId + ", user=" + user + ", description=" + description
				+ ", justification=" + justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode
				+ ", status=" + status + ", total=" + total + ", reasonForRejection=" + reasonForRejection
				+ ", submittedDate=" + submittedDate + "]";
	}
}