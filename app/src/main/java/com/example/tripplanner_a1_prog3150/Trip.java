/*
* FILE : Trip.java
* PROJECT : Trip Planner A1 - PROG3150
* PROGRAMMER : Gerritt Hooyer
* FIRST VERSION : 2022-02-07
* DESCRIPTION : Includes all methods and data members related to the trip.
 */

package com.example.tripplanner_a1_prog3150;

import java.io.Serializable;

/*
* NAME  : Trip
* PURPOSE : To store all data related to the trip and provide
*           basic methods for manipulation of that data.
 */
public class Trip implements Serializable {

    private String destination;
    private String origin;
    private float ticketPrice;
    private float totalTicketPrice;
    private int numPeople;

    //Getters
    public String getDestination()
    {
        return destination;
    }

    public String getOrigin()
    {
        return origin;
    }

    public float getTicketPrice()
    {
        return ticketPrice;
    }

    public float getTotalTicketPrice() { return totalTicketPrice; }

    public int getNumPeople() { return numPeople; }

    //Setters
    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public void setTicketPrice(float ticketPrice)
    {
        //Do not alter ticket price if trying to set it to a value < 0
        if(ticketPrice >= 0)
        {
            this.ticketPrice = ticketPrice;
        }
    }

    public void setTotalTicketPrice(float totalTicketPrice)
    {
        //Do not alter total ticket price if trying to set it to a value < 0
        if(totalTicketPrice >= 0)
        {
            this.totalTicketPrice = totalTicketPrice;
        }
    }

    public void setNumPeople(int numPeople)
    {
        //Do not alter number of people if trying to set it to a value < 0
        if(numPeople >= 0)
        {
            this.numPeople = numPeople;
        }
    }


}
