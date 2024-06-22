package com.dkepr.VehicleRouting.entities;

public class RoutePointNew {
        private int id;
        private String routeName;
        private int sequence;
        private boolean atHome;
        private double coordinates;
        private int vehicle;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRouteName() {
            return routeName;
        }

        public void setRouteName(String routeName) {
            this.routeName = routeName;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public boolean isAtHome() {
            return atHome;
        }

        public void setAtHome(boolean atHome) {
            this.atHome = atHome;
        }

        public double getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(double coordinates) {
            this.coordinates = coordinates;
        }

        public int getVehicle() {
            return vehicle;
        }

        public void setVehicle(int vehicle) {
            this.vehicle = vehicle;
        }
    }
