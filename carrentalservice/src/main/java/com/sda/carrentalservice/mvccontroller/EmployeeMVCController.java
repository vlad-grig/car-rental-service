package com.sda.carrentalservice.mvccontroller;

import com.sda.carrentalservice.entity.Employee;
import com.sda.carrentalservice.service.BranchService;
import com.sda.carrentalservice.service.EmployeeService;
import com.sda.carrentalservice.service.RentalOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeMVCController {

    private final EmployeeService employeeService;
    private final BranchService branchService;
    private final RentalOfficeService rentalOfficeService;

    @Autowired
    public EmployeeMVCController(EmployeeService employeeService, BranchService branchService, RentalOfficeService rentalOfficeService) {
        this.employeeService = employeeService;
        this.branchService = branchService;
        this.rentalOfficeService = rentalOfficeService;
    }

    @GetMapping
    public String showEmployeesBranchesRentalOffices(Model model) {
        model.addAttribute("employees", this.employeeService.findAllEmployees());
        model.addAttribute("branches", this.branchService.findAllBranches());
        model.addAttribute("rentalOffices", this.rentalOfficeService.findAllRentalOffices());
        return "index";
    }

    @GetMapping(path = "/employee/{id}")
    public String deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }

    @PostMapping(path = "/employee/add")
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-employee";
        } else {
            this.employeeService.saveEmployee(employee);
            return "redirect:/";
        }
    }

    @GetMapping(path = "/employee/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping(path = "/employee/update")
    public String editEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-employee";
        } else {
            this.employeeService.saveEmployee(employee);
            return "redirect:/";
        }
    }

    @GetMapping(path = "/employee/edit/{id}")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("employee", this.employeeService.findEmployeeById(id));
        return "edit-employee";
    }

    @GetMapping(path = "/employees")
    public String showEmployees(Model model) {
        List<Employee> allEmployees = this.employeeService.findAllEmployees();
        model.addAttribute("employees", allEmployees);
        return "employee-list";
    }
}
