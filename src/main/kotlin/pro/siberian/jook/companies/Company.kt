package pro.siberian.dynamicsql.companies

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import pro.siberian.dynamicsql.employees.Employee
import java.time.LocalDateTime

@Table("companies")
data class Company(

    @Id
    val id: Long?,

    val name: String?,

    @Column("date_create")
    val dateCreate: LocalDateTime?,

    @MappedCollection(idColumn = "company_id", keyColumn = "id")
    val employees: List<Employee>? = listOf(),
)