public class PremiumBus extends BusType{
    
    private int refundCut;
    private int premiumFee;
    
    public PremiumBus(int id, String from, String to, int rows, double price, int refundCut, int premiumFee)  {
        super(id, from, to, rows, price);
        this.refundCut=refundCut;
        this.premiumFee=premiumFee;
    }

    public int getPremiumFee()  {
        return premiumFee;
    }

    public void setPremiumFee(int premiumFee)  {
        this.premiumFee = premiumFee;
    }

    
    public int getRefundCut()  {
        return refundCut;
    }

    public void setRefundCut(int refundCut)   {
        this.refundCut = refundCut;
    }


} 
  