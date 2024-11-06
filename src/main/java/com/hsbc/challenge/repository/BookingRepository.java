package com.hsbc.challenge.repository;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hsbc.challenge.model.entities.Booking;

@Repository
public class BookingRepository {
	private static final Logger logger = LoggerFactory.getLogger(BookingRepository.class);

	// Slot time in milliseconds
	private final int TIME_SLOT_SIZE = 2 * 60 * 60000;
	// Represents the model, the bookings are saved depending of the year, month and
	// day
	private Map<Integer, Map<Integer, Map<Integer, List<Booking>>>> data = new HashMap<>();

	/**
	 * Get the bookings of a soecific day
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return the list of bookings if exist or empty list if not
	 */
	public List<Booking> getAllBookings(int year, int month, int day) {

		data.forEach((k, v) -> logger.debug("Key: " + k + ": Value: " + v));

		List<Booking> bookings = new LinkedList<>();

		if (data.get(year) != null && data.get(year).get(month) != null && data.get(year).get(month).get(day) != null)
			bookings = data.get(year).get(month).get(day);

		return bookings;
	}

	/**
	 * Add booking
	 * 
	 * @param booking
	 * @return the booking if the value could be added or null if not
	 */
	public Booking addBooking(Booking booking) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(booking.getTime());

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (data.get(year) == null) {
			Map<Integer, Map<Integer, List<Booking>>> yearData = new HashMap<Integer, Map<Integer, List<Booking>>>();
			data.put(year, yearData);
		}

		if (data.get(year).get(month) == null) {
			Map<Integer, List<Booking>> monthData = new HashMap<Integer, List<Booking>>();
			data.get(year).put(month, monthData);
		}

		if (data.get(year).get(month).get(day) == null) {
			List<Booking> dayData = new LinkedList<Booking>();
			dayData.add(booking);
			data.get(year).get(month).put(day, dayData);

			return booking;
		}

		List<Booking> dayData = data.get(year).get(month).get(day);

		List<Booking> fdayData = dayData.stream().sorted(Comparator.comparing(Booking::getTime)).filter(b -> {
			long timeb = b.getTime().getTime();

			long time = booking.getTime().getTime();
			
			if(Math.abs(time -timeb) < TIME_SLOT_SIZE)
				return true;
			
			return false; 
		}).collect(Collectors.toList());

		if (fdayData.isEmpty()) {
			dayData.add(booking);
			data.get(year).get(month).put(day, dayData);
			return booking;
		}

		return null;
	}

}