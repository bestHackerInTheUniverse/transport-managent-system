
public class Bus {
	private String id;
    private Station beginStation;
    private Destination busStop; // Điểm đến

    public Bus(String id, Station beginStation, Destination busStop) {
        this.id = id;
        this.beginStation = beginStation;
        this.busStop = busStop;
    }

    public String getId() { return id; }
    public Station getBeginStation() { return beginStation; }
    public Destination getBusStop() { return busStop; }

    @Override
    public String toString() {
        return "Bus " + id + ": " + beginStation.getName() + " -> " + busStop.getName();
    }
}
