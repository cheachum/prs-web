package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Request;
import com.prs.db.RequestRepository;


@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	private RequestRepository requestRepo;
	
	// list - return all requests
	@GetMapping("/")
	public JsonResponse listRequests() {
		JsonResponse jr = null;
		try  {
			jr = JsonResponse.getInstance(requestRepo.findAll());			
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// get - return 1
	@GetMapping("/{id}")
	public JsonResponse getRequest(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.findById(id));
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// add -
	@PostMapping("/")
	public JsonResponse addRequest(@RequestBody Request r) {
		
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.save(r));
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
	public JsonResponse updateRequest(@RequestBody Request r) {
		JsonResponse jr = null;
		try {
			if (requestRepo.existsById(r.getId())) {
				jr = JsonResponse.getInstance(requestRepo.save(r));
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating Request.  id: "+
											r.getId() + " doesn't exist!");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// delete -
	@DeleteMapping("/{id}")
	public JsonResponse deleteRequest(@PathVariable int id) {
		// delete a request
		JsonResponse jr = null;
		
		try {
			if (requestRepo.existsById(id)) {
				requestRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful!");
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error deleting Request.  id: "+
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