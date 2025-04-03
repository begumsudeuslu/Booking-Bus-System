import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BusSystem {

    private static List<BusType> buses = new ArrayList<>();
    public static StringBuilder output = new StringBuilder();
    
    /**
     * This method take inputs and arrange the mehtods as suitable for the comment lines input.
     * @param lines this is lines of string list.
     * @return string becouse of the write to output file.
    */
    public static String system(String[] lines)  {
        String lastCommand ="";
        for (String line: lines)    {
            String[] tokens = line.split("\t");
            String command = tokens[0];            //first argument is comment.
            
            lastCommand = command;
            switch (command)  {
                case "INIT_VOYAGE":
                    output.append("COMMAND: ").append(line).append("\n");

                    if (tokens[1].equals("Premium") && tokens.length ==9)  {        //Check the bus type and length of the line.
                        initializeVoyage(tokens);     

                    } else if (tokens[1].equals("Standard") && tokens.length ==8)   {      //Check the bus type and length of the line.
                        initializeVoyage(tokens);

                    } else if (tokens[1].equals("Minibus") && tokens.length ==7)   {        //Check the bus type and length of the line.
                        initializeVoyage(tokens);

                    }  else  {
                        output.append("ERROR: Erroneous usage of \"INIT_VOYAGE\" command!" + "\n");      // If length of the line is not correct, print error massage. 
                    }
                    break;
 
                case "SELL_TICKET":
                    output.append("COMMAND: ").append(line).append("\n");
                    
                    if (tokens.length==3)   {       //Check the length of the line.
                        sellTicket(tokens, buses);
                    
                    } else {
                        output.append("ERROR: Erroneous usage of \"SELL_TICKET\" command!" + "\n");   // If length of the line is not correct, print error massage.
                    }
                    break;

                case "REFUND_TICKET":
                    output.append("COMMAND: ").append(line).append("\n");
                    
                    if (tokens.length==3)   {           //Check the length of the line.
                        refundTicket(tokens, buses);
                    
                    } else  {
                        output.append("ERROR: Erroneous usage of \"REFUND_TICKET\" command!" + "\n");      // If length of the line is not correct, print error massage.
                    }
                    break;

                case "PRINT_VOYAGE":
                    output.append("COMMAND: ").append(line).append("\n");

                    if (tokens.length != 2)    {            //Check the length of the line.
                        output.append("ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!" + "\n");
                        break;

                    }  else if (!tokens[1].matches("\\d+"))   {                //Check the id is positive.
                        output.append("ERROR: " + tokens[1] + " is not a positive integer, ID of a voyage must be a positive integer!" + "\n");
                        break;
                    
                    } else  {         //If everything correct run the initilize method.
                        printVoyage(tokens, buses);
                    }
                    
                    break;

                case "CANCEL_VOYAGE":
                    output.append("COMMAND: ").append(line).append("\n");

                    if (tokens.length == 2)   {             //Check the length of the line.
                        if (Integer.parseInt(tokens[1]) > 0)  {
                            int voyageId = Integer.parseInt(tokens[1]);
                            cancelVoyage(voyageId, buses);

                        } else  {               //Check the id is positive.
                            output.append("ERROR: "+tokens[1]+" is not a positive integer, ID of a voyage must be a positive integer!" + "\n");
                        }
                    } else  {
                        output.append("ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!" + "\n");       //Check the length of the line.
                    }
                    break;

                case "Z_REPORT":
                    output.append("COMMAND: ").append(line).append("\n");
                    if (tokens.length == 1)  {
                        zReport(buses);
                        
                        
                    } else  {
                        output.append("ERROR: Erroneous usage of \"Z_REPORT\" command!" + "\n");
                    }
                    break;
                default:
                    output.append("COMMAND: ").append(line).append("\n");
                    output.append("ERROR: There is no command namely "+ tokens[0] +"!" + "\n");    
            }
        } 

        if (!lastCommand.equals("Z_REPORT"))  {
            zReport(buses);
        }
        return output.toString();
    }

    /**
     * This method initilize the constructor and add to buses list. 
     * @param params  its parameter which is in the line.
     */
    public static void initializeVoyage(String[] params)  {
        String busType = params[1];
        int id = Integer.parseInt(params[2]);
        String from = params[3];
        String to = params[4];
        int rows = Integer.parseInt(params[5]);
        double price = Double.parseDouble(params[6]);
        if (id <= 0) {    //check the id.
           output.append("ERROR: "+id+" is not a positive integer, ID of a voyage must be a positive integer!" + "\n");
        } else if (rows <= 0) {   //check the rows.
            output.append("ERROR: "+ rows +" is not a positive integer, number of seat rows of a voyage must be a positive integer!" + "\n");
        } else if (price <= 0)  {     //check the price
            int iprice = (int) price;  
            output.append("ERROR: "+ iprice + " is not a positive number, price must be a positive number!" + "\n");
        }  else if (findVoyage(id, buses) != null)  {     //check the bus is already exists or not.
            output.append("ERROR: There is already a voyage with ID of "+ id +"!" + "\n");
        } else {
            if (busType.equals("Premium") && params.length == 9) {
                int refundCut = Integer.parseInt(params[7]);
                int premiumFee = Integer.parseInt(params[8]);
                if (refundCut<0) {    //check the refuncut count.
                    output.append("ERROR: "+ refundCut+" is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!" + "\n");
                } else if (premiumFee<0)  {        //check premiumfee count.
                    output.append("ERROR: "+premiumFee+" is not a non-negative integer, premium fee must be a non-negative integer!" + "\n");
                }  else {
                    PremiumBus premiumBus = new PremiumBus(id, from, to, rows, price, refundCut, premiumFee);    //initlize constuctor.
                    buses.add(premiumBus);   // add to list.
                    output.append("Voyage " + id + " was initialized as a premium (1+2) voyage from " + from + " to " + to + " with " + String.format("%.2f", price) + " TL priced " + rows * 2 + " regular seats and " + String.format("%.2f", price * (100 + premiumFee) / 100 )+ " TL priced " + rows + " premium seats. Note that refunds will be " + refundCut + "% less than the paid amount."  + "\n");
                }
               
            } else if (busType.equals("Standard") && params.length == 8) {
                int refundCut = Integer.parseInt(params[7]);
                if (refundCut<0) {    //check the refundcut count.
                    output.append("ERROR: "+ refundCut+" is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!" + "\n");
                } else {
                    StandartBus standardBus = new StandartBus(id, from, to, rows, price, refundCut);    //initilize constuctor.
                    buses.add(standardBus);    // addd to list.
                    output.append("Voyage " + id + " was initialized as a standard (2+2) voyage from " + from + " to " + to + " with " + String.format("%.2f", price) + " TL priced " + rows * 4 + " regular seats. Note that refunds will be " + refundCut + "% less than the paid amount." + "\n");
                }
            } else if (busType.equals("Minibus") && params.length == 7) {
                buses.add(new Minibus(id, from, to, rows, price));   // initlize constructor.
                output.append("Voyage " + id + " was initialized as a minibus (2) voyage from " + from + " to " + to + " with " + String.format("%.2f", price) +" TL priced " + rows * 2 + " regular seats. Note that minibus tickets are not refundable." + "\n");
            } else {
                output.append("Not correct bus type." + "\n");
            }
        }
    }

    /**
     * This methods return bus if exists becouse we will use it, if not exist return null.
     * @param voyId  is integer id of the bus.
     * @param buses  is BusType of the buses.
     * @return  if exists return bus, if is not return null.
     */
    private static BusType findVoyage(int voyId, List<BusType> buses)   {
        for (BusType bus: buses) {
            if(bus.getId() == voyId)  {
                return bus;
            }
        }
        return null;
    }

    /**
     * This method arrange seats are occuppied and also calculate revenue for every seat which is sold. 
     * @param params  list of the parameter in the line.
     * @param buses  is BusType of the buses.
     */
    private static void sellTicket(String[] params, List<BusType> buses)  {
        int id = Integer.parseInt(params[1]);
        String[] seatNum = params[2].split("_");
    
        BusType bus = findVoyage(id, buses);
        if (bus != null)   {             //check the bus is exists.
            boolean allAvailable = true;       //check the seats are not occuppied.
            boolean writeOrNot = false;         //if you write already, you dont write anything.
            double soldAmount = 0.0;
            double premiumFee=0.0;
            StringBuilder soldSeats = new StringBuilder();
    
            for (String aSeat: seatNum)   {
                int intSeat = Integer.parseInt(aSeat);
                if (intSeat <= 0)  {    //check the seats are positive.
                    allAvailable = false;
                    writeOrNot=true;            // We write the output line so writeOrNot is false anymore.
                    output.append("ERROR: "+ aSeat +" is not a positive integer, seat number must be a positive integer!" + "\n");
                    break;
                  
                } else if (0 < intSeat && intSeat <= bus.getSeats().size())     {     //check the seat are on the range.
                    Seat seat = bus.getSeats().get(intSeat-1);
    
                    if (bus instanceof PremiumBus && intSeat % 3 == 1) {            //check the premium seat.
                        premiumFee=((PremiumBus) bus).getPremiumFee();
                        soldAmount += bus.getPrice() * (premiumFee / 100);    //calculate premiumfee extra.
                        soldAmount += bus.getPrice();                       //add price

                    } else {
                        soldAmount+= bus.getPrice();     //if regular seat add just price.
                    }
    
                    if (seat.isOccuppied())  {       //if occuppied exit the loop.
                        allAvailable = false;              //change as false because these seats not suit anymore.
                        break;

                    } else  {       //if none of them occupied calculate and sell the seats.
                        if (soldSeats.length() > 0)  {
                            soldSeats.append("-");
                        }
                        soldSeats.append(intSeat);
                    }
                    
                }  else {
                    allAvailable = false;
                    writeOrNot = true;   // We write the output line so writeOrNot is false anymore.
                    output.append("ERROR: There is no such a seat!" + "\n");
                    break;
                }
            }
            if (allAvailable && !writeOrNot)   {      //if seats are suit
                for ( String aSeat: seatNum)  {
                    int intSeat = Integer.parseInt(aSeat);
                    Seat seat = bus.getSeats().get(intSeat-1);
                    seat.setOccuppied(true);      //change the occupied value because these seats are sold.
                }
                output.append("Seat " + soldSeats.toString() + " of the Voyage " + id + " from " + bus.getFrom() + " to " + bus.getTo() + " was successfully sold for " + String.format("%.2f", soldAmount) + " TL." + "\n");
                bus.setRevenue(bus.getRevenue()+soldAmount);     //add solded amount to revenue.
            
            }  else if (!writeOrNot)  {     //if its out of the index write error line.
                output.append("ERROR: One or more seats already sold!" + "\n");
            }
        }  else  {          //if bus is null, its mean this bus id not correct.
            output.append("ERROR: There is no voyage with ID of " + id +"!" + "\n");
        }
    }
    
    /**
     * This method refund the ticket while checking suitable or not.
     * @param params  list of arguments in the line.
     * @param buses   BusType of the busType
     */
    private static void refundTicket(String[] params, List<BusType> buses)   {
        int id = Integer.parseInt(params[1]);
        String[] seatNumbers = params[2].split("_");
        
        BusType bus = findVoyage(id, buses);
        if (bus!= null)  {          //check the bus is exists.
            boolean allAvaliable = true;
            StringBuilder refundSeats = new StringBuilder();
            double totalRefundAmount = 0;

            if ( bus instanceof Minibus)  {    //you cant refund minibus tickets.
                output.append("ERROR: Minibus tickets are not refundable!\n");

            } else {       //bus type can be premium or standard
                for (String strSeat: seatNumbers)  {
                    int intSeat = Integer.parseInt(strSeat);

                    if (0 < intSeat && intSeat <= bus.getSeats().size())  {    //if seats is in the range of the bound
                        Seat seat = bus.getSeats().get(intSeat-1);
                        
                        if (seat.isOccuppied()) {    //Check the occuppied, if is occuppied you can refund seat.
                            double refundAmount = calculateRefundAmount(bus, intSeat);    // calculate given money count.
                            totalRefundAmount += refundAmount;
                            if ( refundSeats.length()>0)  {
                                refundSeats.append("-");
                            }
                            refundSeats.append(intSeat);
                        
                        } else  {      // this mean occuppied is false, other say seat is already empty.
                            allAvaliable = false;
                            output.append("ERROR: One or more seats are already empty!" + "\n");
                            break;
                        }

                    }  else if (intSeat <= 0) {      //check the seat number, it has to be positive integer.
                        allAvaliable = false;
                        output.append("ERROR: "+ strSeat +" is not a positive integer, seat number must be a positive integer!" + "\n");
                        
                    }  else {       //if its out of the bound
                        allAvaliable = false;
                        output.append("ERROR: There is no such a seat!" + "\n");
                    }
                }

                if (allAvaliable)  {    //if all occuppied
                    for (String strSeat: seatNumbers)  {
                        int intSeat = Integer.parseInt(strSeat);
                        Seat seat = bus.getSeats().get(intSeat-1);
                        seat.setOccuppied(false);
                    }

                    output.append("Seat " + refundSeats + " of the Voyage " + id + " from " + bus.getFrom() + " to " + bus.getTo() + " was successfully refunded for " + String.format("%.2f",totalRefundAmount) + " TL."+ "\n");
                    bus.setRevenue(bus.getRevenue()-totalRefundAmount);    //subtract the given money or refunded money and set revenue again.
                }
            }
        } else  {    //if bus is null, its mean this bus id not correct.
            output.append("ERROR: There is no voyage with ID of " + id +"!" + "\n");
        }
    }
    
    /**
     * This method calculate the given money.
     * @param bus   BusType of the buses.
     * @param seatNum   integer value of seat number.
     * @return      double is amount of the refunded money.
     */
    private static double calculateRefundAmount(BusType bus, int seatNum)  {
        if (bus instanceof PremiumBus)  {    //if standart bus calculate refunded money
            double price = ((PremiumBus) bus).getPrice();
            double premiumFee=((PremiumBus) bus).getPremiumFee();
            double refundCut =((PremiumBus) bus).getRefundCut();
            if (seatNum % 3==1) {    //check the seat is premium
                return price*(100+premiumFee)/100 * (1-((refundCut)/100));   //if premium its refunded amount
            } else {
                return price * (((100 - refundCut) / 100));   //if seat is regular
            }
        } else if (bus instanceof StandartBus)  {      //if standart bus calculate refunded money
            double price = ((StandartBus) bus).getPrice();
            double refundCut =((StandartBus) bus).getRefundCut();
            return price * (((100 - refundCut) / 100));
        } else  {
            return 0;
        }
    }

    /**
     * Cancel the voyage and refund money.
     * @param voyageId   integer of the bus as id
     * @param buses     BusType of the bus
     */
    private static void cancelVoyage(int voyageId, List<BusType> buses)   {
        BusType bus = findVoyage(voyageId, buses);
        String outputSeat;
        int seatNum;
        if (bus!= null)  {    //check the bus exits
            double refundedAmount = 0;
            outputSeat = generateSeatPlan(bus);     //take the seat plan with another method.
            
            for (Seat seat: bus.getSeats())  {
                if (seat.isOccuppied())  {          //if seat is occuppied.
                    seatNum = seat.getSeatNumber();
                    if (bus instanceof PremiumBus && seatNum%3==0)   {     //check the premium seat
                        double price = ((PremiumBus) bus).getPrice();
                        double premiumFee=((PremiumBus) bus).getPremiumFee();
                        refundedAmount += price*(1+premiumFee/100);
                    }  else  {              //if seat is regular
                        refundedAmount += bus.getPrice();
                    }
                }
                seat.setOccuppied(false);     //cancelled the voyage, so all seats are empty anymore.
            }
            bus.setRevenue(bus.getRevenue()-refundedAmount);   //set revenue again
            output.append("Voyage "+voyageId+" was successfully cancelled!\nVoyage details can be found below:\n");    //write information line
            output.append("Voyage " + bus.getId()+"\n"+bus.getFrom() + "-" + bus.getTo()+"\n");    //write information line
            output.append(outputSeat);        //add seatplan to output
            output.append("\nRevenue: "+ String.format("%.2f", bus.getRevenue())+ "\n");    //write revenue
            buses.remove(bus);     //finally remove the bus
        
        } else  {           //if voyage is not exists
            output.append("ERROR: There is no voyage with ID of " + voyageId + "!"+ "\n");    
        } 
    }

    /**
     * This method arrange the seat planning according to occuppied or not occuppied.
     * @param bus    BusType of the bus
     * @return      string as a plan of seats
     */
    private static String generateSeatPlan(BusType bus)  {

        String output = "";
        int seatsPerRow = 0;     //initilize and arrange seat number of the per row
        if (bus instanceof Minibus)  {
            seatsPerRow = 2;
        } else if (bus instanceof StandartBus)  {
            seatsPerRow = 4;
        } else if (bus instanceof PremiumBus)  {
            seatsPerRow = 3;
        }
        List<Seat> seats = bus.getSeats();

        for (int i = 0; i < seats.size(); i++)  {
            if (i % seatsPerRow==0 && i!=0)  {
                output+="\n";   //add new line for beginning of the row
            }
            //if seat is occuppied use X, if is empty use *
            if (bus instanceof Minibus)   {
                if (i % seatsPerRow == 1) {
                    output+=seats.get(i).isOccuppied() ? "X":"*";
                } else {
                    output+=seats.get(i).isOccuppied() ? "X ":"* "; 
                }   
            }if(bus instanceof PremiumBus)   {
                if (i % seatsPerRow == 0)  {
                    output+=seats.get(i).isOccuppied() ? "X ":"* ";
                    output+="| ";
                } else if ( i % seatsPerRow == 2) {
                    output+=seats.get(i).isOccuppied() ? "X":"*";
                }  else {
                    output+=seats.get(i).isOccuppied() ? "X ":"* ";
                }
            }if(bus instanceof StandartBus)  {
                if ((i+1) % seatsPerRow == 2 )   {
                    output+=seats.get(i).isOccuppied() ? "X ":"* ";
                    output+="| ";
                } else if(i%seatsPerRow == 3)  {
                    output+=seats.get(i).isOccuppied() ? "X":"*";
                }  else {
                    output+=seats.get(i).isOccuppied() ? "X ":"* ";
                }
            }          
        }
        return output;
    }
    
    /**
     * Print voyage information. This method return anything, just add output directly.
     * @param params   list of the parameters on the line.
     * @param buses     BusType of the buses.
     */
    private static void printVoyage(String[] params, List<BusType> buses)  {
        int voyageId = Integer.parseInt(params[1]);
        BusType bus = findVoyage(voyageId, buses);

        if (bus!= null) {     //check the bus is exists.
            output.append("Voyage " + bus.getId() + "\n");    //add information line
            output.append(bus.getFrom() + "-" + bus.getTo()+ "\n");
            String seatPlan = generateSeatPlan(bus);  
            output.append(seatPlan + "\n");         //add seat plan
            output.append("Revenue: "+ String.format("%.2f", bus.getRevenue())+ "\n");    //write revenue
        }  else  {       //if bus is not exists print error
            output.append("ERROR: There is no voyage with ID of " +  voyageId + "!"+ "\n");   
        }
    }

    /**
     * This method print all buses and also printing according to bus id, starts with smaller id and goes bigger.
     * @param buses   BusType of the buses
     */
    private static void zReport(List<BusType> buses) {
        output.append("Z Report:\n----------------"+ "\n");
        if (buses.isEmpty()) {     // if there is no bus 
            output.append("No Voyages Available!\n----------------"+ "\n");
        } else {       //if buses exists.
            Collections.sort(buses, Comparator.comparingInt(BusType::getId));  //Sort the list of buses based on id with help of the this library.
    
            for (int i = 0; i < buses.size(); i++) {
                BusType bus = buses.get(i);
                output.append("Voyage " + bus.getId()+ "\n");    //write information
                output.append(bus.getFrom() + "-" + bus.getTo()+ "\n");     
                String seatPlan = generateSeatPlan(bus);     //take the seat plan
                output.append(seatPlan + "\n");      //add seat plan
                output.append("Revenue: "+ String.format("%.2f", bus.getRevenue())+ "\n");    //set revenue
                output.append("----------------\n");
            }
        }
    }

}