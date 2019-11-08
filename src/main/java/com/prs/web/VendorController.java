package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import com.prs.business.Vendor;
import com.prs.db.VendorRepository;


@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {
	@Autowired
	private VendorRepository vendorRepo;
	
	// list - return all vendors
	@GetMapping("/")
	public JsonResponse listVendors() {
		JsonResponse jr = null;
		try  {
			jr = JsonResponse.getInstance(vendorRepo.findAll());			
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// get - return 1
	@GetMapping("/{id}")
	public JsonResponse getVendor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.findById(id));
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// add -
	@PostMapping("/")
	public JsonResponse addVendor(@RequestBody Vendor v) {
		
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.save(v));
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
	public JsonResponse updateVendor(@RequestBody Vendor v) {
		JsonResponse jr = null;
		try {
			if (vendorRepo.existsById(v.getId())) {
				jr = JsonResponse.getInstance(vendorRepo.save(v));
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating Vendor.  id: "+
											v.getId() + " doesn't exist!");
			}
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e.getMessage());
		}
		return jr;
	}
	
	// delete -
	@DeleteMapping("/{id}")
	public JsonResponse deleteVendor(@PathVariable int id) {
		// delete a user
		JsonResponse jr = null;
		
		try {
			if (vendorRepo.existsById(id)) {
				vendorRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful!");
			}
			else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error deleting Vendor.  id: "+
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