package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.entity.DealEntity;
import com.example.demo.models.service.DealServiceImpl;

@Controller
@RequestMapping("/deal")
public class DealController {

	@Autowired
	DealServiceImpl dealService;

	@GetMapping({ "", "/" })
	public String ver(Model model) {
		List<DealEntity> deals = dealService.findAll();
		model.addAttribute("deals", deals);
		return "deal_all";
	}

	@GetMapping("/create")
	public String crear(Model model) {
		DealEntity deal = new DealEntity();
		model.addAttribute("deal", deal);
		model.addAttribute("buttonText", "Crear Deal");
		return "deal_form";
	}

	@PostMapping("/save")
	public String save(DealEntity deal, Model model) {
		dealService.save(deal);
		return "redirect:/deal";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		dealService.delete(id);
		return "redirect:/deal";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		DealEntity deal = dealService.findOne(id);
		model.addAttribute("deal", deal);
		model.addAttribute("buttonText", "Actualizar Deal");
		return "deal_form";
	}
}
