
public class TicketTrain extends Ticket {
    private String scheduleId;
    private String seatNumber;

    public TicketTrain(String id, double price, String scheduleId, String seatNumber) {
        super(id, price);
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
    }

    @Override
    public String getTicketInfo() {
        return "TICKET [" + id + "] | Schedule: " + scheduleId + " | Seat: " + seatNumber + " | Price: $" + price;
    } 
    }


