import java.util.List;
import java.util.ArrayList;

public class BusType {
    protected int id;
    protected String from;
    protected String to;
    protected int rows;
    protected double price;
    private List<Seat> seats;
    private double revenue;
     
    public BusType(int id, String from, String to, int rows, double price)   {
        this.id= id;
        this.from=from; 
        this.to=to;
        this.rows=rows;
        this.price=price;
        this.seats = arrangeSeat();
        this.revenue = 0.00;
    }


    public List<Seat> arrangeSeat( )  {
        int seatsPerRow;
        if (this instanceof Minibus)   {
            seatsPerRow = 2;
        } else if (this instanceof StandartBus)  {
            seatsPerRow = 4;
        } else  {
            seatsPerRow = 3;
        }
        
        int seatCount = rows * seatsPerRow;
        List<Seat> arrangedSeats = new ArrayList<>();
        for (int i = 0; i < seatCount; i++)   {
            arrangedSeats.add(new Seat(i, false));
        }
        return arrangedSeats;
    }


    public int getId()  {
        return id;
    }

    public void setId(int id)  {
        this.id = id;
    }

    public String getFrom()  {
        return from;
    }

    public void setFrom(String from)   {
        this.from = from;
    }

    public String getTo()  {
        return to;
    }

    public void setTo(String to)   {
        this.to = to;
    }

    public int getRows()  {
        return rows;
    }

    public void setRow(int rows)   {
        this.rows = rows;
    }

    public double getPrice()  {
        return price;
    }

    public void setPrice(int price)   {
        this.price = price;
    }

    public List<Seat> getSeats()  {
        return seats;
    }

    public void setSeats(List<Seat> seats)   {
        this.seats = seats;
    }

    public double getRevenue()  {
        return revenue;
    }

    public void setRevenue(double revenue)  {
        this.revenue = revenue;
    }
}
