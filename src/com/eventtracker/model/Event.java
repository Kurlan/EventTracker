package com.eventtracker.model;

import lombok.Data;

@Data
public class Event {
	private final String id;
	private final String description;
	private final String type;
}
