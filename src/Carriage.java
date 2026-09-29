import java.util.HashMap;
import java.util.Map;

public class Carriage {
	private String id;
    private int numberOfSeats;
    // Tối ưu: Dùng HashMap để kiểm tra trạng thái ghế O(1) thay vì duyệt mảng
    private Map<String, Boolean> seatMap; 

    public Carriage(String id, int numberOfSeats) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
        this.seatMap = new HashMap<>();
        // Khởi tạo ghế trống (false = chưa đặt)
        for (int i = 1; i <= numberOfSeats; i++) {
            seatMap.put(id + "-S" + i, false);
        }
    }

    public boolean isSeatAvailable(String seatId) {
        return seatMap.containsKey(seatId) && !seatMap.get(seatId);
    }

    public boolean bookSeat(String seatId) {
        if (isSeatAvailable(seatId)) {
            seatMap.put(seatId, true); // Đánh dấu đã đặt
            return true;
        }
        return false;
    }

    public String getId() { return id; }
    public int getNumberOfSeats() { return numberOfSeats; }
}
