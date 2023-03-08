package com.cg.tournament.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="rating")
@Table(name="rating")
public class Rating {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable = false)
    private Long id;

    @Column(name="userid")
    private Long userId;

    @Column(name="playerid")
    private Long playerId;

    @Column(name="rating")
    private double rating;

    public Rating() {
    }

    public Rating(Long userId, Long playerId, double rating) {
        this.userId = userId;
        this.playerId = playerId;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
