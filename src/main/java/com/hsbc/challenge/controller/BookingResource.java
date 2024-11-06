package com.hsbc.challenge.controller;

import java.util.List;

import com.hsbc.challenge.model.entities.Booking;
import com.hsbc.challenge.service.BookingService;

import io.muserver.rest.ApiResponse;
import io.muserver.rest.Description;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/booking")
@Description(value = "A booking", details = "Use this API to get and create bookings")
public class BookingResource {
	BookingService bookingService;

	public BookingResource(BookingService bookingService) {
		super();
		this.bookingService = bookingService;
	}

	@GET
	@Path("/{year}/{month}/{day}")
	@Produces("application/json")
	@Description("Gets bookings")
	@ApiResponse(code = "200", message = "Success")
	@ApiResponse(code = "404", message = "No booking founds")
	public List<Booking> get(@PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day) {
		return bookingService.getAllBookings(year, month, day);
	}

	@POST
	@Consumes("application/json")
	@Description("Adds a new booking")
	@ApiResponse(code = "201", message = "The booking was added")
	@ApiResponse(code = "400", message = "A problem in add operation")
	public Response add(Booking booking) {
		Booking bookingAdded = bookingService.addBooking(booking);

		if (bookingAdded != null)
			return Response.status(201).build();

		return Response.status(400).build();
	}

}