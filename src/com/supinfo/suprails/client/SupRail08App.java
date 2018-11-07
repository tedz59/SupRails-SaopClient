package com.supinfo.suprails.client;

import com.supinfo.suprails.web.service.soap.CustomerOrder;
import com.supinfo.suprails.web.service.soap.Trip;
import com.supinfo.suprails.web.service.soap.TripRessource;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

public class SupRail08App {

    private static final String TRIP_WSDL = "http://localhost:8080/SupRails_war_exploded/soap/trips?WSDL";
 
    private static final String TRIP_QNAME = "{http://soap.service.web.suprails.supinfo.com/}soap/trips";

    private static TripRessource tripRessource;
    
    private static Scanner scanner;

    public static void main(String[] args) throws Exception {

        tripRessource = getTripRessource();

        scanner = new Scanner(System.in);
        
        while(true) {
            
            System.out.println("\nSupRails client menu : ");
            System.out.println("\t1. Show trips");
            System.out.println("\t2. Order trip");
            System.out.println("\t3. Exit\n");
            
            System.out.print("Enter your choice (1, 2 or 3) : ");
            String choice = scanner.next();
            System.out.println();
            
            if(choice.equals("1")) {
                showTrips();
            } else if(choice.equals("2")) {
                createTrip();
            } else if(choice.equals("3")) {
                System.out.println("Bye.");
                System.exit(1);
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Permet d'initialiser le client du webservice.
     * @return le client.
     * @throws MalformedURLException
     */
    private static TripRessource getTripRessource() throws MalformedURLException {
        Service tripService = Service.create(URI.create(TRIP_WSDL).toURL(), QName.valueOf(TRIP_QNAME));
        return tripService.getPort(TripRessource.class);
    }

    private static void createTrip() {
        
        long tripId = 0;
        String firstname = "", lastname = "", email = "";
        
        while(tripId == 0 || firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
            
            if(tripId == 0) {
                
                showTrips();
                System.out.print("Choose the trip to order : ");
                
                try {
                    tripId = Long.valueOf(scanner.next());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value.");
                }
            }
            
            if(firstname.isEmpty()) {
                System.out.print("Firstname : ");
                firstname = scanner.next();
            }
            
            if(lastname.isEmpty()) {
                System.out.print("Lastname : ");
                lastname = scanner.next();
            }
            
            if(email.isEmpty()) {
                System.out.print("Email : ");
                email = scanner.next();
            }
        }
        
        System.out.println("\nCreating order...");
        CustomerOrder result = tripRessource.processCustomerOrder(tripId, firstname, lastname, email);
        
        if(result != null) {
            System.out.println("Done with success.");
        } else {
            System.out.println("There was an error, aborted");
        }
    }
    
    private static void showTrips() {
        
        System.out.println("Trips list :");
        
        List<Trip> trips = tripRessource.getAll();
        
        for(Trip trip : trips) {
            System.out.println("\t" + trip.getId() + " : " + trip.getDepartureStation().getName() + " - " + trip.getArrivalStation().getName());
        }
    }
    
}
