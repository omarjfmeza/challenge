package com.hsbc.challenge.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hsbc.challenge.model.entities.Booking;
import com.hsbc.challenge.service.BookingService;

class BookingServiceTest {

	@Test
	void addBookingTest() {
		BookingService bookingService = new BookingService(new BookingRepository());
		Booking booking = Booking.builder().customer("Omar").tableSize(2).time(new Date()).build();
		
		Booking bookingSaved = bookingService.addBooking(booking);
		
		assertNotNull(bookingSaved);
		assertEquals(booking, bookingSaved);
	}

	@Test
	void getAllBookingsTest() {
		Date current = new Date();
		BookingService bookingService = new BookingService(new BookingRepository());
		Booking booking = Booking.builder().customer("Omar").tableSize(2).time(current).build();
		
		bookingService.addBooking(booking);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(booking.getTime());

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		List<Booking> bookings = bookingService.getAllBookings(year, month, day);
		
		assertNotNull(bookings);
		assertTrue(bookings.size() == 1);
	}
}
