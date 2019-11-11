package com.prs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.business.Product;
import com.prs.business.Request;
import com.prs.db.LineItemRepository;
import com.prs.db.RequestRepository;




@CrossOrigin
@RestController
@RequestMapping("/line-items")
public class LineItemController {
	@Autowired
	private LineItemRepository lineitemRepo;
	
	@Autowired 
	private RequestRepository requestRepo;
	
	// list - return all line items
	@GetMapping("/")
	public JsonResponse listLineItems() {
		JsonResponse jr = null;
		try  {
			jr = JsonResponse.getInstance(lineitemRepo.findAll());			
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// get - return 1
	@GetMapping("/{id}")
	public JsonResponse getLineItem(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineitemRepo.findById(id));
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	//get --return all LIs for 1 request
	@GetMapping("/lines-for-pr/{id}")
	public JsonResponse getAllLineItems(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineitemRepo.findByRequestId(id));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	// add -
	@PostMapping("/")
	public JsonResponse addLineItem(@RequestBody LineItem l) {
		
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineitemRepo.save(l));
			recalculateTotal(l.getRequest().getId());
		}
		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}
	
	// update -
	@PutMapping("/")
	public JsonResponse updateLineItem(@RequestBody LineItem l) {
		JsonResponse jr = null;
		try {
			if (lineitemRepo.existsById(l.getId())) {
				jr = JsonResponse.getInstance(lineitemRepo.save(l));
				recalculateTotal(l.getRequest().getId());
				
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating line item.  id: "+
											l.getId() + " doesn't exist!");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// delete -
	@DeleteMapping("/{id}")
	public JsonResponse deleteLineItem(@PathVariable int id) {
		// delete a request
		JsonResponse jr = null;
		
		try {
			if (lineitemRepo.existsById(id)) {		
				LineItem l = lineitemRepo.findById(id).get();
				lineitemRepo.deleteById(id);
				recalculateTotal(l.getRequest().getId());
				jr = JsonResponse.getInstance("Delete succesful!");
				
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error deleting line item.  id: "+
											id + " doesn't exist!");
			}
		}
		catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	
	// method recalcs total and save it 
	private void recalculateTotal(int requestID) {
		// get a list of line items 
		List<LineItem> lines = lineitemRepo.findByRequestId(requestID);
		// loop through list to get total
		double total = 0.0;
		for (LineItem line : lines) {
			Product p = line.getProduct();
			total += p.getPrice()*line.getQuantity();
		}
		// save that total in the instance of request
		Request r = requestRepo.findById(requestID).get();
		r.setTotal(total);
		requestRepo.save(r);
	}


}