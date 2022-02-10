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
    private int tripGoers;
    private float totalTicketCost;
    private float hotelCost;
    private int nights;
    private float totalHotelCost;
    private float totalCost;

    public Trip()
    {
        destination = "";
        origin = "";
        ticketPrice = 0;
        tripGoers = 0;
        hotelCost = 0;
        nights = 0;
        totalTicketCost = 0;
        totalHotelCost = 0;
        totalCost = 0;
    }


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

    public int getTripGoers() { return tripGoers; }

    public float getHotelCost() { return hotelCost; }

    public int getNights() { return nights; }

    public float getTotalHotelCost() { return totalHotelCost; }

    public float getTotalTicketCost() { return totalTicketCost; }

    public float getTotalCost() { return totalCost; }

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
            //We also need to update the total ticket cost
            updateTotalTicketCost();
        }
    }

    public void setTripGoers(int tripGoers)
    {
        if (tripGoers >= 1 && tripGoers <= 4)
        {
            this.tripGoers = tripGoers;
            //We also need to update the total ticket cost
            updateTotalTicketCost();
        }
    }

    public void setHotelCost(float hotelCost)
    {
        if(hotelCost >= 0)
        {
            this.hotelCost = hotelCost;
            //We also need to update the total hotel cost
            updateTotalHotelCost();
        }
    }

    public void setNights(int nights)
    {
        //Do not alter the # of nights
        if (nights >= 1)
        {
            this.nights = nights;
            //We also need to update the total hotel cost
            updateTotalHotelCost();
        }
    }

    //Total updaters
    public void updateTotalTicketCost()
    {
        totalTicketCost = ticketPrice * tripGoers;
        updateTotalCost();
    }

    public void updateTotalHotelCost()
    {
        totalHotelCost = hotelCost * nights;
        updateTotalCost();
    }

    public void updateTotalCost()
    {
        totalCost = totalHotelCost + totalTicketCost;
    }


}
