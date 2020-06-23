# parking_lot


#Requirements for setup and run this Application
1. Java-1.8
2. Junit-4.13
3. Apache Maven 3.5




#How to run
1. Run ./setup.sh from the home folder, it will compile, build, runs tests and creates a jar.
    a) For eample  $ ./setup.sh
2. Run parking_lot.sh to run the application. It runs in 2 modes
    a) To read commands from a file run: $ ./parking_lot.sh  src/main/resources/inputFiles/file_inputs.txt
    b) To run the commands from a shell interface run: $ ./parking_lot.sh
        
Following are the commands you can use to run commands from the prompt
1. create_parking_lot {capacity: Number}
2. park {Registration Number: String} {color: string}
3. leave {slot Number: Number}
4  status
5. registration_numbers_for_cars_with_colour {color: string}
6  slot_numbers_for_cars_with_colour {color: string}
7. slot_number_for_registration_number {Registration Number: string}



#Thinking process
* A building can have multiple levels of parking lots.
* A parking lot can have multiple parking slots.
* Slots can be of muliple types based on vehicle i.e Car, Bike, electric-car(in this case need a slot with charging facility), slot for pregnant women, slot for handicapped personnel.
* There can be multiple ways to allot a slot to a vehicle which can change as per the parkinglot.(Strategy pattern)

#Entities:
* Vehicle -> car
* Ticket - issued after each parking. It can have info like the level on which vehicle is parked, the time the vehicle was parked and vehicle info.
* MultiLevelParking- a single building with multiple levels. Its responsibility is to tell on which level a vehicle should be parked at.
* ParkingLevel - a level of parking in a building/MLCP. Its responsibility is to tell on which slot a vehicle should be parked at.
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