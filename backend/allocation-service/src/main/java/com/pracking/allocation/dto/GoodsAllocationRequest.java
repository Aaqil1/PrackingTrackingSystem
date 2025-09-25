package com.pracking.allocation.dto;

import jakarta.validation.constraints.Min;

public class GoodsAllocationRequest {
    @Min(1)
    private double goodsVolume;
    @Min(1)
    private int goodsQuantity;

    public double getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(double goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }
}
