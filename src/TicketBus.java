import java.time.LocalDate;

public class TicketBus extends Ticket {
    private Bus bus;
    private LocalDate dayUse;

    public TicketBus(String id, double price, Bus bus, LocalDate dayUse) {
        super(id, price);
        this.bus = bus;
        this.dayUse = dayUse;
    }

    @Override
    public String getTicketInfo() {
        return "VÉ BUS [" + id + "] | Tuyến: " + bus.getId() + 
               " (" + bus.getBusStop().getName() + ") | Ngày: " + dayUse + " | Giá: $" + price;
    }

}
