package pro.siberian.dynamicsql

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pro.siberian.dynamicsql.companies.Company
import pro.siberian.dynamicsql.companies.CompanyService
import pro.siberian.dynamicsql.employees.Employee
import pro.siberian.dynamicsql.employees.EmployeeService
import java.time.LocalDateTime
import javax.validation.constraints.Pattern


@RestController
@RequestMapping("/dynamicsql")
@Validated
class Controller(
    private val companyServ: CompanyService,
    private val employeeServ: EmployeeService,
) {

    @GetMapping("/companies")
    fun getCompanies(
        @RequestParam("name") name: String?,
        @RequestParam("birth_years") birthYears: List<Int>?,
        @RequestParam("has_full_time") hasFullTime: Boolean,
        @RequestParam("has_freelance") hasFreelance: Boolean,
        @RequestParam("date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dateFrom: LocalDateTime?,
        @RequestParam("date_till") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dateTill: LocalDateTime?,
        @RequestParam("salary_from") salaryFrom: Long?,
        @RequestParam("salary_till") salaryTill: Long?,
        @RequestParam("sort", defaultValue = "id_old")
        @Pattern(regexp = "date_create_new|date_create_old|id_new|id_old") sort: String,
    ): Set<Company> {
        return companyServ.findAll(
            name,
            birthYears,
            hasFullTime,
            hasFreelance,
            dateFrom,
            dateTill,
            salaryFrom,
            salaryTill,
            sort
        )
    }

    @GetMapping("/employees")
    fun getEmployees(): List<Employee> {
        return employeeServ.findAll()
    }

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCompany(
        @RequestParam("name") name: String,
    ) {
        companyServ.save(Company(0, name, LocalDateTime.now()))
    }

    @PostMapping("/employee")
    fun createEmployee(
        @RequestParam("name") name: String,
        @RequestParam("status") @Pattern(regexp = "full_time_emp|freelance_emp") status: String,
        @RequestParam("salary") salary: Long,
        @RequestParam("year_birth") yearBirth: Int,
        @RequestParam("company_id") companyId: Long,
    ): ResponseEntity<Any> {
        if (companyServ.findById(companyId) == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("company not found")

        employeeServ.save(Employee(0, name, status, salary, yearBirth, companyId))

        return ResponseEntity.status(HttpStatus.CREATED).body("")
    }
}