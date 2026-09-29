import java.util.ArrayList;
import java.util.List;

public class Station {
    private String id;
    private String name;
    private List<Bus> busList; // Biến này cần được khởi tạo

    public Station(String id, String name) {
        this.id = id;
        this.name = name;
        
        // --- QUAN TRỌNG: PHẢI CÓ DÒNG NÀY ---
        this.busList = new ArrayList<>(); 
        // ------------------------------------
    }

    public void addBus(Bus bus) {
        // Nếu lỡ quên khởi tạo ở trên thì khởi tạo tại đây để tránh lỗi
        if (this.busList == null) {
            this.busList = new ArrayList<>();
        }
        busList.add(bus);
    }

    public List<Bus> getBusList() {
        return busList;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; }
}