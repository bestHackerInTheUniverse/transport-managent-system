import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Locomotive {
	String id;
    double speed;
    Stack<String> maintenanceHistory; // Stack để lưu lịch sử bảo trì mới nhất lên đầu

    public Locomotive(String id, double speed) {
        this.id = id;
        this.speed = speed;
        this.maintenanceHistory = new Stack<>();
}
    public void addMaintenance(String info) {
        maintenanceHistory.push(info);
    }}
