package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.User;
import com.prs.db.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	// list - return all users
	@GetMapping("/")
	public JsonResponse listUsers() {
		JsonResponse jr = null;
		try  {
			jr = JsonResponse.getInstance(userRepo.findAll());			
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// get - return 1
	@GetMapping("/{id}")
	public JsonResponse getUser(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.findById(id));
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// add -
	@PostMapping("/")
	public JsonResponse addUser(@RequestBody User u) {
		
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.save(u));
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
	public JsonResponse updateUser(@RequestBody User u) {
		JsonResponse jr = null;
		try {
			if (userRepo.existsById(u.getId())) {
				jr = JsonResponse.getInstance(userRepo.save(u));
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating User.  id: "+
											u.getId() + " doesn't exist!");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// delete -
	@DeleteMapping("/{id}")
	public JsonResponse deleteUser(@PathVariable int id) {
		// delete a user
		JsonResponse jr = null;
		
		try {
			if (userRepo.existsById(id)) {
				userRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful!");
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error deleting User.  id: "+
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