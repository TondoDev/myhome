package org.tondo.myhome.thyme.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tondo.myhome.dto.ExpenseDO;
import org.tondo.myhome.dto.ExpenseInDayDO;
import org.tondo.myhome.svc.enumsvc.EnumNames;
import org.tondo.myhome.svc.enumsvc.EnumSvc;
import org.tondo.myhome.svc.service.ExpenseSvc;
import org.tondo.myhome.thyme.pagemodel.ExpensePageModeDefault;
import org.tondo.myhome.thyme.pagemodel.ExpensePageModel;

@Controller
@RequestMapping("/expense")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@Autowired
	private EnumSvc enumService;
	
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public String findForCurrentMonth(Model model) {
		YearMonth now = YearMonth.now();
		new ExpensePageModel(model, now.getMonthValue(), now.getYear())
			.dataSupplier(() -> expenseService.getExpenses(now.getMonthValue(), now.getYear()))
			.typesSupplier(() -> enumService.getEnumValues(EnumNames.EXPENSES))
			.summarySupplier(() -> this.expenseService.getSummaryByMonth(now.getMonthValue(), now.getYear()) )
			.nextMonth(expenseService.findNextMonth(now.getMonthValue(), now.getYear()))
			.previousMonth(expenseService.findPreviousMonth(now.getMonthValue(), now.getYear()))
			.target("/expense/current")
		.apply();
		return "detail";
	}
	
	@RequestMapping(value = "/year/{year}/month/{month}", method = RequestMethod.GET)
	public String findByQuery(Model model, @PathVariable("month") int month, @PathVariable("year") int year) {
		YearMonth examinedMonth = YearMonth.of(year, month);
		new ExpensePageModel(model, examinedMonth.getMonthValue(), examinedMonth.getYear())
			.dataSupplier(() -> expenseService.getExpenses(examinedMonth.getMonthValue(), examinedMonth.getYear()))
			.typesSupplier(() -> enumService.getEnumValues(EnumNames.EXPENSES))
			.summarySupplier(() -> this.expenseService.getSummaryByMonth(examinedMonth.getMonthValue(), examinedMonth.getYear()))
			.inputEnabled(isCurrentMont(month, year))
			.nextMonth(expenseService.findNextMonth(month, year))
			.previousMonth(expenseService.findPreviousMonth(month, year))
			.target("/expense/year/" + year+ "/month/" + month)
		.apply();
		return "detail";
	}
	
	@RequestMapping(value = "/prev/year/{year}/month/{month}", method = RequestMethod.GET)
	public String findPreviousMonth(Model model, @PathVariable("month") int month, @PathVariable("year") int year) {
		System.out.println("=== " + expenseService.findPreviousMonth(year, month));
		return "redirect:/expense/year/" + year+ "/month/" + month;
	}
	
	@RequestMapping(value = "/next/year/{year}/month/{month}", method = RequestMethod.GET)
	public String findNextMonth(Model model, @PathVariable("month") int month, @PathVariable("year") int year) {
		
		return "";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model model) {
		
		new ExpensePageModeDefault(model)
			.typesSupplier(() -> enumService.getEnumValues(EnumNames.EXPENSES))
			.dataSupplier(() -> expenseService.getExpenses())
			.summarySupplier(() -> this.expenseService.getTotalSummary())
		.apply();

		return "expense";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("target") String target, @ModelAttribute("expenseForm") @Valid ExpenseInDayDO expense, BindingResult bindingResult, RedirectAttributes redirect) {
		if (!bindingResult.hasErrors()) {
			LocalDate modified = expense.getDate().withDayOfMonth(expense.getDay());
			expense.setDate(modified);
			expenseService.save(expense);
			expense.setNote(null);
		} else {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.expenseForm", bindingResult);
		}
		redirect.addFlashAttribute("expenseForm", expense);
		return target == null ? "redirect:/expense/" : ("redirect:" + target); 
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public String addExpenseAdmin(@ModelAttribute("expenseForm") @Valid ExpenseDO expense, BindingResult bindingResult, RedirectAttributes redirect) {
		if (!bindingResult.hasErrors()) {
			expenseService.save(expense);
			expense.setNote(null);
		} else {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.expenseForm", bindingResult);
		}
		redirect.addFlashAttribute("expenseForm", expense);
		return "redirect:/expense/"; 
	}
	
	@RequestMapping(value="/{expenseId}", method = RequestMethod.DELETE)
	public String deleteExpense(@PathVariable Long expenseId, @RequestParam(value = "target", required = false) String target) {
		this.expenseService.delete(expenseId, false);
		return target == null || target.isEmpty() ? "redirect:/expense/" : ("redirect:" +  target);
	}
	
	@RequestMapping(value="/admin/{expenseId}", method = RequestMethod.DELETE)
	public String deleteExpenseAdmin(@PathVariable Long expenseId) {
		this.expenseService.delete(expenseId);
		return "redirect:/expense/";
	}
	
	// is calling 
	@InitBinder
	public void paramBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(dateFormat, true));
	}
	
	private static boolean isCurrentMont(int month, int year) {
		YearMonth now = YearMonth.now();
		return now.getMonthValue() == month && now.getYear() == year;
	}
}
