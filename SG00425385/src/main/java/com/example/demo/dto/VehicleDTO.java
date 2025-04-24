package com.example.demo.dto;

public class VehicleDTO {
    private String reg;
    private String make;
    private String model;
    private OwnerDTO owner;
    private MechanicDTO mechanic;

    public VehicleDTO() {
    }

    // Getters and setters
    public String getReg() { return reg; }
    public void setReg(String reg) { this.reg = reg; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public OwnerDTO getOwner() { return owner; }
    public void setOwner(OwnerDTO owner) { this.owner = owner; }

    public MechanicDTO getMechanic() { return mechanic; }
    public void setMechanic(MechanicDTO mechanic) { this.mechanic = mechanic; }

    // Inner classes for nested objects
    public static class OwnerDTO {
        private String cid;
        private String name;

        public String getCid() { return cid; }
        public void setCid(String cid) { this.cid = cid; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class MechanicDTO {
        private String mid;
        private String name;
        private Double salary;
        private GarageDTO garage;

        public String getMid() { return mid; }
        public void setMid(String mid) { this.mid = mid; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Double getSalary() { return salary; }
        public void setSalary(Double salary) { this.salary = salary; }

        public GarageDTO getGarage() { return garage; }
        public void setGarage(GarageDTO garage) { this.garage = garage; }
    }

    public static class GarageDTO {
        private String gid;
        private String location;
        private Integer budget;

        public String getGid() { return gid; }
        public void setGid(String gid) { this.gid = gid; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public Integer getBudget() { return budget; }
        public void setBudget(Integer budget) { this.budget = budget; }
    }
}
