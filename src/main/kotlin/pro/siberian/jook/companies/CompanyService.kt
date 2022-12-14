package pro.siberian.dynamicsql.companies

import org.jooq.DSLContext
import org.springframework.stereotype.Service
import pro.siberian.dynamicsql.employees.Employee
import pro.siberian.example.Tables
import java.time.LocalDateTime

@Service
class CompanyService(private val companyRepo: CompanyRepository, private val dslContext: DSLContext) {

    fun findAll(
        name: String?,
        birthYears: List<Int>?,
        hasFullTime: Boolean,
        hasFreelance: Boolean,
        dateFrom: LocalDateTime?,
        dateTill: LocalDateTime?,
        salaryFrom: Long?,
        salaryTill: Long?,
        sort: String,
    ): Set<Company> {
        val companies = Tables.COMPANIES
        val employees = Tables.EMPLOYEES

        val dsl = dslContext
            .select(companies.ID, companies.NAME, companies.DATE_CREATE)
            .from(companies)
            .leftJoin(employees)
            .on(companies.ID.eq(employees.COMPANY_ID))

        if (name != null)
            dsl.where(companies.NAME.containsIgnoreCase(name))
        if (hasFreelance)
            dsl.where(employees.STATUS.eq("freelance_emp"))
        if (hasFullTime)
            dsl.where(employees.STATUS.eq("full_time_emp"))
        if (!birthYears.isNullOrEmpty())
            dsl.where(employees.YEAR_BIRTH.`in`(birthYears))
        if (salaryFrom != null && salaryTill != null)
            dsl.where(employees.SALARY.between(salaryFrom, salaryTill))
        if (dateFrom != null && dateTill != null)
            dsl.where(companies.DATE_CREATE.between(dateFrom, dateTill))

        when (sort) {
            "date_create_new" -> dsl.orderBy(companies.DATE_CREATE.desc())
            "date_create_old" -> dsl.orderBy(companies.DATE_CREATE.asc())
            "id_new" -> dsl.orderBy(companies.ID.desc())
            else -> dsl.orderBy(companies.ID.asc())
        }

        return dsl
            .fetch()
            .map { r ->
                val company: Company = r.into(Company::class.java)
                company.copy(employees = dslContext
                    .select()
                    .from(employees)
                    .where(employees.COMPANY_ID.eq(company.id)).fetch()
                    .map { r2 -> r2.into(Employee::class.java) })
            }.toSet()
    }

    fun findAll() = companyRepo.findAll().toList()

    fun findById(id: Long): Company? = companyRepo.findById(id).orElse(null)

    fun save(company: Company) = companyRepo.save(company)
}