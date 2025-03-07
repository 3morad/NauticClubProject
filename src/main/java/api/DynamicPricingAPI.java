package api;

import entities.Equipment;

public class DynamicPricingAPI {
    public double calculateDynamicPrice(Equipment equipment, int demandFactor) {
        double basePrice = equipment.getPrice();
        return basePrice * (1 + (demandFactor * 0.1));
    }
}

