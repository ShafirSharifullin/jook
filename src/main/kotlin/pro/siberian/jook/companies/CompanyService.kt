package pro.siberian.dynamicsql.companies

import org.jooq.DSLContext
import org.springframework.stereotype.Service
import pro.siberian.dynamicsql.employees.Employee
import pro.siberian.example.Tables

@Service
class CompanyService(private val companyRepo: CompanyRepository, private val dsl: DSLContext) {

    fun findAll(status: String): Set<Company> {
        return dsl
            .select(Tables.COMPANIES.ID, Tables.COMPANIES.NAME, Tables.COMPANIES.DATE_CREATE)
            .from(Tables.COMPANIES)
            .leftJoin(Tables.EMPLOYEES)
            .on(Tables.COMPANIES.ID.eq(Tables.EMPLOYEES.COMPANY_ID))
//            .where(Tables.COMPANIES.NAME.containsIgnoreCase("IT tech"))
            .orderBy(Tables.COMPANIES.DATE_CREATE.desc())
            .fetch()
            .map { r ->
                val company: Company = r.into(Company::class.java)
                company.copy(employees = dsl
                    .select()
                    .from(Tables.EMPLOYEES)
                    .where(Tables.EMPLOYEES.COMPANY_ID.eq(company.id)).fetch()
                    .map { r2 -> r2.into(Employee::class.java) })
            }.toSet()
    }

    fun findAll() = companyRepo.findAll().toList()

    fun findById(id: Long): Company? = companyRepo.findById(id).orElse(null)

    fun save(company: Company) = companyRepo.save(company)
}