
public class Destination {
	    private String name;
	    private String address;

	    public Destination(String name, String address) {
	        this.name = name;
	        this.address = address;
	    }

	    public String getName() { return name; }
	    
	    @Override
	    public String toString() { return name + " (" + address + ")"; }
	}

