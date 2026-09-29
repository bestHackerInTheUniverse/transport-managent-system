

	abstract class Ticket {
	    protected String id;
	    protected double price;
	    protected boolean status;

	    public Ticket(String id, double price) {
	        this.id = id;
	        this.price = price;
	        this.status = true; // Active
	    }
	    
	    public abstract String getTicketInfo();
	}

