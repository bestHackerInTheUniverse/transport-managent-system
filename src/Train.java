import java.util.LinkedList;
	public class Train {
	    private String name;
	    private Locomotive locomotive;
	    private LinkedList<Carriage> carriages; // LinkedList phù hợp để nối toa

	    public Train(String name, Locomotive locomotive) {
	        this.name = name;
	        this.locomotive = locomotive;
	        this.carriages = new LinkedList<>();
	    }

	    public void addCarriage(Carriage c) {
	        carriages.add(c);
	    }

	    public int getTotalSeats() {
	        return carriages.stream().mapToInt(Carriage::getNumberOfSeats).sum();
	    }

	    public Carriage getCarriage(String id) {
	        for (Carriage c : carriages) {
	            if (c.getId().equals(id)) return c;
	        }
	        return null;
	    }

	    public String getName() { return name; }
	    public LinkedList<Carriage> getCarriages() { return carriages; }
	}

