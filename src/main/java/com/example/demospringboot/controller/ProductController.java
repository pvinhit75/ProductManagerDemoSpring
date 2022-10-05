package com.example.demospringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demospringboot.model.Product;
import com.example.demospringboot.service.ProductService;

import ch.qos.logback.core.joran.conditional.IfAction;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = {"/login" })
	public String loginHomePage() {
		return "login"; 
	}

	@GetMapping("/")
	public String viewHomePage(Model model, @Param("keyword") String keyword) {
		List<Product> listProducts = productService.listAll(keyword);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("keyword", keyword);

		return "index";
	}

	@GetMapping("/new")
	public String showNewProductForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);

		return "new_product";
	}
	

	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") Product product) {
		productService.save(product);

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showEditProductForm(@PathVariable Long id, Model model) {

		model.addAttribute("product", productService.get(id));

		return "edit_product";
	}

	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Long id) {
		productService.delete(id);

		return "redirect:/";
	}

}
