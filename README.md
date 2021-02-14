
# fetch-reward-points
Fetch Reward Points Api

THE FOLLOWING API CREATED HAS 3 endpoints :

1) To add the Transaction of a particular user with the Payer Name points. : http://localhost:8080/v1/api/transaction  
    After Running the code in IntelliJ or any IDE we will use this api to add transaction to the user for an Example hit this link after running in postaman under the post request 
    and in body add the following
    
    {
    "pairName": "Dan",
    "points": 300,
    "userId": 1
   }
   
       {
    "pairName": "Uni",
    "points": 200,
    "userId": 1
   }
   
       {
    "pairName": "Dan",
    "points": -200,
    "userId": 1
   }
   
       {
    "pairName": "Mill",
    "points": 10000,
    "userId": 1
   }
   
       {
    "pairName": "Dan",
    "points": 1000,
    "userId": 1
   }
   
   After adding the transaction accordingly now we will check the second API which is the deduct API
   
   
   2) In this we will have a api which is a get Method and just tell how many points deducted from each payer so on that case what we do hit the api 
   http://localhost:8080/v1/api/deduct/1/5000    in postmasn the last two parameter you see is nothing but the userID and Number of points to be deducted. The output will show        basically the points deucted from each payer name
   
   
   3)  The third and the last api is just to show how many points left from each payer to hit that api it is a Get Method and hot the api
   http://localhost:8080/v1/api/deduct/1     here the last /1 is nothing but the userId you want the payerName transactions with
      

