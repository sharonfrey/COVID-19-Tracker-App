# COVID-19-Tracker-App

Coronavirus Tracker App

Uses COVID-19 Tracking info from the following APIs:
https://documenter.getpostman.com/view/10877427/SzYW2f8n?version=latest#ccdfe411-a0e8-40f2-908b-a6ecdddad111
https://documenter.getpostman.com/view/1294665/SzYdSGX6?version=latest#c01f0b06-8873-4e72-b99c-a2e02b33d5fb

App has the following elements:


· User Login page. 

    · A user must login to the app in order to access any other part. 
    
    · If a user inputs the wrong info 5 times, the login button will stop working.
    
    · Default username and password are "admin". 
    
   
· Search location/favorite. 

    · A user may search for different locations to see COVID-19 stats for. 
    
    · They may also store a list of their favorite locations for easy access. 
    
    · If a user clicks on a location in the list, a search will be made for that location. 
    
    · If a user long clicks on an item on the list, they will be taken to a separate  page to edit/delete location from list.
    

· View Stats by Country/State

    · A volley request is called to search for COVID-19 stats by a given user input.
    
    · The stats displayed on screen are as follows:
    
        · Total Cases
        
        · Active Cases
        
        · Deaths
        
        · Recovered
        
        · Critical
        
        · Tested
        
        · Death Ratio
        
        · Recovery Ratio
        

· View World/Country Graphs

    · 4 graphs will be displayed on page
    
    · Pie Chart - The total number of COVID-19 cases for the top 15 countries will be compared in a pie chart.
    
    · Line Graph - The total number of COVID-19 cases for the past 5 days will be displayed in a line graph for the top 5 countries.
    
    · Line Graph - A predicted total of COVID-19 cases for the next 5 days will be displayed in a line graph for the top 5 countries.
    
    · Bar Graph - Each bar on the bar graph is a count of the total number of COVID-19 cases for the top 15 countries. The bar graph is divided up into 3 parts: number of recoveries, number of deaths, and number of unresolved cases (any person in total case count that      has not been marked as recovered or dead).
    
    
· Latest News

    · A list of total worldwide reports are showed on this page.
    
    · The list goes back to 1/22/2020 when cases were first starting, and shows the user of the timline as the number of cases increased.
      


