package com.felix.labs.category

import data.DocumentStoreHolder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CategoryApplication

fun main(args: Array<String>) {
    runApplication<CategoryApplication>(*args)

    val test = DocumentStoreHolder.getStore()

    val errorMessage = try {
        val newSession = test.openSession()
        val newCategory = Category("Felipe Augusto Felix", "Test", true)
        newSession.store(newCategory)
        newSession.saveChanges()
    } catch (ex: Exception) {
        ex.message
    }

    print(errorMessage)
}


class Category(val name: String, val description: String, isActive: Boolean) {

}