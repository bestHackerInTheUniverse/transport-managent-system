import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class ScheduleDetail {
	private String id;
    private LocalDateTime departureTime;
    private Train train;
    private Route route;
    // Queue để xử lý danh sách chờ đặt vé (Reservation Queue)
    private Queue<String> reservationQueue; 

    public ScheduleDetail(String id, LocalDateTime departureTime, Train train, Route route) {
        this.id = id;
        this.departureTime = departureTime;
        this.train = train;
        this.route = route;
        this.reservationQueue = new LinkedList<>();
}public String getId() { 
	return id; 
	}
public Train getTrain() { 
	return train; 
	}
public Route getRoute() { 
	return route; 
	}
public LocalDateTime getDepartureTime() {
	return departureTime; 
	}

@Override
public String toString() {
    return id + ": " + train.getName() + " - " + route.getName() + " @ " + departureTime;
}}
