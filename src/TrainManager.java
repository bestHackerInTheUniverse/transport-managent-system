import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainManager {
    private static TrainManager instance;

    // Quản lý Tàu
    private Map<String, Train> trains;
    private Map<String, ScheduleDetail> schedules;
    
    // Quản lý Bus (Mới thêm)
    private Map<String, Bus> buses;

    // Quản lý chung
    private List<Ticket> soldTickets;

    private TrainManager() {
        trains = new HashMap<>();
        schedules = new HashMap<>();
        buses = new HashMap<>(); // Khởi tạo map cho Bus
        soldTickets = new ArrayList<>();
    }

    public static synchronized TrainManager getInstance() {
        if (instance == null) instance = new TrainManager();
        return instance;
    }

    // --- Các hàm cho Tàu ---
    public void addTrain(Train t) { trains.put(t.getName(), t); }
    public void addSchedule(ScheduleDetail s) { schedules.put(s.getId(), s); }
    public Map<String, ScheduleDetail> getAllSchedules() { return schedules; }

    // --- Các hàm cho Bus (Mới) ---
    public void addBus(Bus b) {
        buses.put(b.getId(), b);
        // Tự động thêm Bus vào danh sách của trạm khởi hành
        b.getBeginStation().addBus(b); 
    }
    
    public Map<String, Bus> getAllBuses() { return buses; }

    // Logic đặt vé Tàu
    public String bookTrainTicket(String scheduleId, String carriageId, String seatId, double price) {
        ScheduleDetail schedule = schedules.get(scheduleId);
        if (schedule == null) return "Lỗi: Không tìm thấy lịch tàu!";
        
        Carriage carriage = schedule.getTrain().getCarriage(carriageId);
        if (carriage == null) return "Lỗi: Không tìm thấy toa!";

        if (carriage.bookSeat(seatId)) {
            String ticketId = "TRN-" + System.currentTimeMillis();
            TicketTrain ticket = new TicketTrain(ticketId, price, scheduleId, seatId);
            soldTickets.add(ticket);
            return "Thành công: " + ticket.getTicketInfo();
        }
        return "Thất bại: Ghế đã có người đặt!";
    }

    // Logic đặt vé Bus (Mới)
    public String bookBusTicket(String busId, LocalDate date, double price) {
        Bus bus = buses.get(busId);
        if (bus == null) return "Lỗi: Không tìm thấy xe Bus!";

        // Xe bus thường không chọn ghế cụ thể như tàu, chỉ cần mua vé lên xe
        String ticketId = "BUS-" + System.currentTimeMillis();
        TicketBus ticket = new TicketBus(ticketId, price, bus, date);
        soldTickets.add(ticket);
        
        return "Thành công: " + ticket.getTicketInfo();
    }
}