public class StandartBus extends BusType{
    
    private int refundCut;
    
    public StandartBus(int id, String from, String to, int rows, double price, int refundCut)  {
        super(id, from, to, rows, price);
        this.refundCut = refundCut;
    }
    
    public int getRefundCut()  {
        return refundCut;
    }

    public void setRefundCut(int refundCut)   {
        this.refundCut = refundCut;
    }

}
 