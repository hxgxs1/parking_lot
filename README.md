# parking_lot


#Requirements for setup and run this Application
1. Java-1.8
2. Junit-4.13
3. Apache Maven 3.5




#How to run
* 


#Thinking process
* A building can have multiple levels of parking lots.
* A parking lot can have multiple parking slots.
* Slots can be of muliple types based on vehicle i.e Car, Bike, electric-car(in this case need a slot with charging facility), slot for pregnant women, slot for handicapped personnel.
* There can be multiple ways to allot a slot to a vehicle which can change as per the parkinglot.(Strategy pattern)

#Entities:
* Vehicle -> car
* Ticket - issued after each parking
* MultiLevelParking- a single building with multiple levels
* ParkingLevel - a level of parking in a building/MLCP
* Slot - a single parking slot in a parkingLevel

#Behaviours
* A vehicle is allotted a particular slot in a parkingLevel based on some parking strategy.
* A vehicle is issued a ticket.
* A vehicle leaves a slot in a parking lot marking the slot empty.
* Queries on current status of the parkinglot based on vehicle color and registration Number.

#Misc thoughts
* Need some sort of command executor interface to run user commands.
* In memory DB to take care of queries and storage.
* Test cases on commands and data.