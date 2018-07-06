#Setup System#
   mvn clean package
   docker-compose build
   docker-compose up
 
#Replicate The Fault
Step 1:
    Login
Step 2:
    Search for a ticket using the following info:
        Starting Place: Shang Hai
        Terminal Place: Su hou
        Date: After today
        Train Type: GaoTie DongChe
Step 3:
    Click [Booking]
Step 4:
    Click [Refresh Contacts]
    Select one of the Contacts and click [Select]
Step 5: 
    Click [Confirm Ticket]
    You may try some more times if you get a "Success". If the fault occurs, you will get an [Error] and you
    will see the exception logs in console.  

#Tip
   In order to simplify the process of log collection, we add a service name click-twice.
   If you click [Confirm Ticket], a request will be sent to click-twice, and this service will send two
request to ticket-preserve asynchronous. In some cases the fault will occurs.
  
        

        
