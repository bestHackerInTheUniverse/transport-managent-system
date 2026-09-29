import java.util.LinkedList;

public class Route {
	 private String id;
     private String name;
     private LinkedList<RoutePart> routeParts; // Tối ưu cho việc thêm/xóa chặng

     public Route(String id, String name) {
         this.id = id;
         this.name = name;
         this.routeParts = new LinkedList<>();
     }

     public void addRoutePart(RoutePart part) {
         routeParts.add(part);
     }
     
     public double getTotalDistance() {
         return routeParts.stream().mapToDouble(p -> p.distanceKm).sum();
     }
     
     public String getName() { return name; }
     
     @Override
     public String toString() { return name + " (" + getTotalDistance() + "km)"; }
 }

