package com.cdac.acts.flightService.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "seats")
@Getter
@Setter
@RequiredArgsConstructor
public class Seat {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(name = "`row`")  // `row` is a reserved SQL keyword; escape it
	    private Integer row;

	    @Column(length = 5)
	    private String col;

	    @ManyToOne
	    @JoinColumn(name = "airplaneId")
	    @JsonIgnore
	    private Airplane airplane;

	    @Column(length = 50)
	    private String type;

	    @Column(name = "created_at")
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	    
	    @Column(name = "isBooked")
	    private boolean isBooked;

}
