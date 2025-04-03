public class Seat {
    private int seatNumber;
    private boolean occuppied;

    public Seat(int seatNumber, boolean occuppied)  {
        this.seatNumber = seatNumber;
        this.occuppied = occuppied;
    }

    public int getSeatNumber()  {
        return seatNumber; 
    }
 
    public boolean isOccuppied()  {
        return occuppied;
    }

    public void setOccuppied(boolean occuppied)  {
        this.occuppied = occuppied;
    }
}
