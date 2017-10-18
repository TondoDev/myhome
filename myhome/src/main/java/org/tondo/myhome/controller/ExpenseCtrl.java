package org.tondo.myhome.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

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
import org.tondo.myhome.enumsvc.EnumNames;
import org.tondo.myhome.enumsvc.EnumSvc;
import org.tondo.myhome.presentation.dropdown.DropdownListCreator;
import org.tondo.myhome.presentation.dropdown.DropdownValue;
import org.tondo.myhome.service.ExpenseDO;
import org.tondo.myhome.service.ExpenseSummaryDO;
import org.tondo.myhome.service.ExpenseSvc;

@Controller
@RequestMapping("/expense")
public class ExpenseCtrl {
	
	@Autowired
	private ExpenseSvc expenseService;
	
	@Autowired
	private EnumSvc enumService;
	
	public ExpenseDO getDefaultFormContent() {
		ExpenseDO formDefault = new ExpenseDO();
		formDefault.setDate(new Date());
		formDefault.setAmount(BigDecimal.valueOf(200));
		return formDefault;
	}
	
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public String findForCurrentMonth(Model model) {
		YearMonth now = YearMonth.now();
		buildPageModel(model, expenseService.getExpenses(now.getMonthValue(), now.getYear()));
		model.addAttribute("summary", this.expenseService.getSummaryByMonth(now.getMonthValue(), now.getYear()));
		return "expense";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String findByQuery(Model model, @RequestParam int month, @RequestParam int year) {
		buildPageModel(model, expenseService.getExpenses(month, year));
		model.addAttribute("inputEnabled", isCurrentMont(month, year));
		model.addAttribute("summary", this.expenseService.getSummaryByMonth(month, year));
		return "expense";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model model) {
		buildPageModel(model, expenseService.getExpenses());
		model.addAttribute("deleteEnabled", true);
		List<ExpenseSummaryDO> summmary = this.expenseService.getTotalSummary();
		model.addAttribute("summary", summmary);
		return "expense";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String addExpense(@ModelAttribute("expenseForm") @Valid ExpenseDO expense, BindingResult bindingResult, RedirectAttributes redirect) {
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
	public String deleteExpense(@PathVariable Long expenseId) {
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
	
	private void buildPageModel(Model model, List<ExpenseDO> expenses) {
		// values to combobox for expense type
		DropdownListCreator<String> dpCreator = new DropdownListCreator<>(DropdownListCreator.STRING_KEY);
		List<DropdownValue<String>> cbExpenseTypeValues = dpCreator
				.addItems(enumService.getEnumValues(EnumNames.EXPENSES)).values();
		model.addAttribute("cbExpenseType", cbExpenseTypeValues);

		if (!model.containsAttribute("expenseForm")) {
			model.addAttribute("expenseForm", getDefaultFormContent());
		}

		// populate list
		model.addAttribute("expenses", expenses);
		// properties
		model.addAttribute("inputEnabled", true);
	}
	
	
	private static boolean isCurrentMont(int month, int year) {
		YearMonth now = YearMonth.now();
		return now.getMonthValue() == month && now.getYear() == year;
	}
}
