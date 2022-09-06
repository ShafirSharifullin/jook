package pro.siberian.dynamicsql.employees

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("employees")
data class Employee(

    @Id
    val id: Int,

    val name: String,

    val status: String,

    val salary: Long,

    @Column("year_birth")
    val yearBirth: Int,

    @JsonIgnore
    @Column("company_id")
    val companyId: Long,
)