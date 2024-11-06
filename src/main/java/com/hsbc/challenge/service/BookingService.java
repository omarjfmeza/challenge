package com.hsbc.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hsbc.challenge.model.entities.Booking;
import com.hsbc.challenge.repository.BookingRepository;

@Service
public class BookingService {
	private BookingRepository bookingRepository;

	public BookingService(BookingRepository bookingRepository) {
		super();
		this.bookingRepository = bookingRepository;
	}

	public List<Booking> getAllBookings(int year, int month, int day) {
		return bookingRepository.getAllBookings(year, month, day);
	}

	public Booking addBooking(Booking booking) {
		return bookingRepository.addBooking(booking);
	}
}
