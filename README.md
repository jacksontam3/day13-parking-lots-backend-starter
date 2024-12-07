Prompt 1:
Now we have additional business requirements, the system should clearly display the current status of all three parking lots, 
showing the license plate of the car parked in each parking position to provide a real time view of parking lot usage

Prompt 2:
generate test cases for this functions

prompt 3:
new business requirement here: For car parking and fetch, 
it should enable the input of a user's license plate number to perform car parking or fetching. 
During Parking, the license plate number should be correctly assigned to an available 
parking position and reflected in the parking lot status display. during retrieval, 
the system should accurately release the corresponding parking slot and update 
the parking lot's status.

prompt 4:
generate corresponding test case of these requirements.

prompt 5:
new business requirements here:  for the parking boy assignment, 
it should support the selection of a specific parking boy for parking or fetching tasks. 
the parking boy will complete the operation based on their designated parking strategy.

prompt 6:

---

**Controller**

Please help me generate a `ParkingLotController` class that allows me to call the `park` and `fetch` methods you created in `ParkingManager`. First, help me generate 4 interfaces:

1. **Get the status of all parking lots**: Returns a `ResponseEntity` containing a `List`. Each element in the list includes the parking lot ID, parking lot name, and the remaining parking spaces in that parking lot. This is a GET request with the path `/parkinglot`.

2. **Park a car based on plate number and parking strategy (STANDARD, SMART, SUPER)**: Returns a `ResponseEntity` containing a `Ticket`. This is a POST request with the path `/parkinglot/park`.

3. **Fetch a car based on plate number**: Returns a `ResponseEntity` containing a `Car`. This is a POST request with the path `/parkinglot/fetch`.

