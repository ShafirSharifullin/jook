package pro.siberian.dynamicsql.companies

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : CrudRepository<Company, Long>