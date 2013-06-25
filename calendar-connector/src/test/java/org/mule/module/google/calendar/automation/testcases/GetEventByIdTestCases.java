/**
 * Mule Google Calendars Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.google.calendar.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.google.calendar.model.Calendar;
import org.mule.module.google.calendar.model.Event;

public class GetEventByIdTestCases extends GoogleCalendarTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getEventById");
			
			// Insert calendar and get reference to retrieved calendar
			Calendar calendar = insertCalendar((Calendar) testObjects.get("calendarRef"));
			
			// Replace old calendar instance with new instance
			testObjects.put("calendarRef", calendar);
			testObjects.put("calendarId", calendar.getId());
			
			// Insert the event
			MessageProcessor flow = lookupFlowConstruct("insert-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// Place the returned event and its ID into testObjects for later access
			Event returnedEvent = (Event) response.getMessage().getPayload();
			testObjects.put("event", returnedEvent);
			testObjects.put("eventId", returnedEvent.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, SanityTests.class})	
	@Test
	public void testGetEventById() {
		try {
			Event originalEvent = (Event) testObjects.get("event");
			
			// Find the event based on previously set ID
			MessageProcessor flow = lookupFlowConstruct("get-event-by-id");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			// No exceptions thrown means that the event was found
			// Perform assertions. Check that the returned event has the same id
			Event returnedEvent = (Event) response.getMessage().getPayload();
			assertTrue(returnedEvent.getId().equals(originalEvent.getId()));		
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String calendarId = testObjects.get("calendarId").toString();
			deleteCalendar(calendarId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
