package edu.harvard.h2ms.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.opencsv.CSVWriter;

import edu.harvard.h2ms.domain.core.Answer;
import edu.harvard.h2ms.domain.core.Event;
import edu.harvard.h2ms.repository.EventRepository;

@Component
@Service("reportService")
public class ReportServiceImpl implements ReportService{
	
	final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventRepository eventRepository;
	
	private Writer stringWriterReport(Map<String, Double> data) {
		
		Writer writer = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
		
		data.forEach((k,v) -> csvWriter.writeNext(new String[] {(String)k,((Double)v).toString()}));
		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
	
	private Writer stringWriterReport(List<List<String>> data) {
		
		Writer writer = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
		
		data.forEach((ls) -> csvWriter.writeNext(ls.toArray(new String[0])));
		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return writer;
	}
	
	
	

	@Override
	public String createReport() {
	
		Map<String, Double> complianceData = userService.findAvgHandWashCompliance();
		Writer writer = stringWriterReport(complianceData);
		
		
		try {
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}
	
	@Override
	public String createEventReport() {
		// Fetches all events from the H2MS database
		List<Event> events = Lists.newArrayList(eventRepository.findAll());
		
		List<List<String>> data = new ArrayList<List<String>>();
		
		// Doesn't assume all events have same answers
		// get all the questions in events
		Set<String> questionKeys = new HashSet<String>();
	    for(Event event : events) {
			for(Answer answer : event.getAnswers()) {
				questionKeys.add(answer.getQuestion().getQuestion());
			}
	    }
	    
	    
		Boolean isHeaderRow = true;
		for(Event event : events) {
			
			if(isHeaderRow == true) {
				List<String> headerRow = new ArrayList<String>();
				headerRow.add("eventId");
				headerRow.add("time");
				headerRow.add("location");
				headerRow.add("observer_id");
				headerRow.add("observer_type");
				headerRow.add("subject_id");
				headerRow.add("subject_type");
				
				// create event question columns
				for(String question : questionKeys) {
					headerRow.add("q_"+question);
				}
				
				data.add(headerRow);
				
				isHeaderRow = false;
			}
			List<String> row = new ArrayList<String>();
			
			row.add(event.getId().toString());
			row.add(event.getTimestamp().toString());			
			row.add(event.getLocation().toString());
			row.add(event.getObserver().getEmail().toString());
			row.add(event.getObserver().getType().toString());
			row.add(event.getSubject().getEmail().toString());
			row.add(event.getSubject().getType().toString());
			
			Map<String, String> answerMap = new HashMap<>();
			for(Answer answer : event.getAnswers()) {
				String q = answer.getQuestion().getQuestion();
				String a = answer.getValue();
				answerMap.put(q, a);
			}
			
			for(String key : questionKeys) {
				if(answerMap.containsKey(key)) {
					row.add(answerMap.get(key));
				} else {
					row.add("");
				}
			}
			
			
			
			data.add(row);	
		}
		
		Writer writer = stringWriterReport(data);
		
		try {
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer.toString();
	}
	
	private class MyReport {
		private String userType;
		private Double complianceRate;
		
		@SuppressWarnings("unused")
		protected MyReport() {}
		
		@SuppressWarnings("unused")
		protected MyReport(String userType, Double complianceRate) {
			this.userType = userType;
			this.complianceRate = complianceRate;
		}

		protected String getUserType() {
			return userType;
		}

		protected void setUserType(String userType) {
			this.userType = userType;
		}

		protected Double getComplianceRate() {
			return complianceRate;
		}

		protected void setComplianceRate(Double complianceRate) {
			this.complianceRate = complianceRate;
		}
	}
}
