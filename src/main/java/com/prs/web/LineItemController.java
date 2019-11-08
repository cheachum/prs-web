package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.db.LineItemRepository;



@CrossOrigin
@RestController
@RequestMapping("/lineitems")
public class LineItemController {
	@Autowired
	private LineItemRepository lineitemRepo;
	
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
	
	// add -
	@PostMapping("/")
	public JsonResponse addLineItem(@RequestBody LineItem l) {
		
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineitemRepo.save(l));
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
				lineitemRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful!");
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

}