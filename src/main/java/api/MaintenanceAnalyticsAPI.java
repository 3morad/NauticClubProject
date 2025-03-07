
package api;

import entities.Equipment;

public class MaintenanceAnalyticsAPI {
    public String predictMaintenance(Equipment equipment) {
        if (equipment.getUsageHours() > 100) {
            return "Maintenance required soon";
        }
        return "Equipment is in good condition";
    }
}
