package com.oliveira.worstmovie.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PrizeInterval {
    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

    public PrizeInterval(String producer, int previousWin, int followingWin) {
        this.producer = producer;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
        this.interval = followingWin - previousWin;
    }
}
