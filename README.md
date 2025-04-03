# Booking-Bus-System
his Bus Reservation System simulates a transportation network where users can manage voyages (bus trips), book seats, and handle operations such as cancellations and refunds.
 The system consists of different types of buses (Minibus, StandardBus, and PremiumBus) and the corresponding logic for managing bookings, seat occupancy, revenues, and refunds.

Key Components:
BusType: The base class for all bus types, containing common properties such as id, from (starting point), to (destination), rows (number of rows of seats), price (price per seat), and seats (a list of Seat objects).
Minibus: A subclass of BusType representing a smaller bus with specific seat arrangements.
StandartBus: A subclass of BusType representing a standard bus with a different seat configuration compared to the minibus.
PremiumBus: A subclass of BusType that extends the functionality with additional features, such as premium seats, refund cuts, and premium fees.
Seat: Represents an individual seat on the bus, which can be either occupied or not.
BusSystem: Contains logic for managing bus trips, including booking, canceling voyages, refund calculations, and generating seat plans.
BookingSystem: The entry point of the system. It reads an input file containing commands and outputs results to a file.

Features:
Seat Management: Each bus has a certain number of rows and seats. Seats are marked as either occupied or unoccupied.
Voyage Management: Voyages are identified by their unique ID. Users can cancel a voyage and get refunds for occupied seats.
Refund Calculation: Premium seats are refunded with a premium fee, while standard seats are refunded at a basic rate. Refund cuts are applied to all types of buses.
Reporting: The system can generate a "Z Report", which displays a detailed breakdown of all buses, their voyages, seat occupancy, and revenue.
Input & Output: The system reads input from a file (using a command-line interface) and outputs the result to another file, making it suitable for batch processing.

Class Overview:
BusType: 
  - Represents a bus and its properties.
  - Contains methods for managing seats and revenue.

Minibus:
  - Inherits from BusType.
  - Has specific seating arrangements (2 seats per row).

StandartBus:
  - Inherits from BusType.
  - Has 4 seats per row.

PremiumBus:
  - Inherits from BusType.
  - Adds features like premiumFee and refundCut.
  - Special logic for premium seat booking and refunding.

Seat:
  - Represents a single seat on a bus.
  - Has properties for tracking occupancy and seat number.

BusSystem:
  - Handles operations like seat booking, canceling voyages, and refund calculations.
  - Manages the seat plan and revenue for each voyage.

BookingSystem:
   - The main entry point of the system.
  - Reads input files, processes commands, and writes the output to a file.
