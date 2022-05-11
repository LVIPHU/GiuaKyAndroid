package com.example.giuakyandroid.model;

public class Statistic {
    public Device device;
    public int count;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Statistic() {
    }

    public Statistic(Device device, int count) {
        this.device = device;
        this.count = count;
    }
}
