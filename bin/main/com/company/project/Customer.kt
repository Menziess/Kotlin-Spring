package com.company.project

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.config.EnableWebFlux
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.Valid

@Entity
data class Customer(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val name: String = "",
        val date_of_birth: Instant = Instant.now(),
        val is_male: Boolean = true
) {

    fun sex(is_male: Boolean): Char {
        if (is_male)
            return 'M'
        else
            return 'F'
    }

    fun Any?.toString(): String = String.format(
            "Customer[id=%d, name='%s', dateOfBirth=%s, sex=%c]",
            id, name, date_of_birth, sex(is_male)
    )
}

@Repository
interface CustomerRepository : JpaRepository<Customer, Long>

@RestController
@RequestMapping("/api")
@EnableWebFlux
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("/customer")
    fun getAllCustomers(): List<Customer> =
            customerRepository.findAll()


    @PostMapping("/customer")
    fun createNewCustomer(@Valid @RequestBody customer: Customer): Customer =
            customerRepository.save(customer)
}
