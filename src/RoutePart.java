import java.util.LinkedList;

public class RoutePart {
	Station beginStation;
    Station endStation;
    double distanceKm;

    public RoutePart(Station begin, Station end, double distance) {
        this.beginStation = begin;
        this.endStation = end;
        this.distanceKm = distance;
    }
}
