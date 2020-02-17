package com.felix.labs.category.handlers

import com.felix.labs.category.data.CategoryRepository
import com.felix.labs.category.model.Category
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.net.URI

class CategoryHandler(private val categoryRepository: CategoryRepository) {
    fun readAll(request: ServerRequest) = ServerResponse.ok().body(categoryRepository.findAll()!!)

    fun readOne(request: ServerRequest): ServerResponse {

        val category = categoryRepository.findOne(request.pathVariable("id"))
                ?: return ServerResponse.notFound().build()
        return ServerResponse.ok().body(category)
    }

    fun create(request: ServerRequest): ServerResponse {
        return categoryRepository.createOne(request.body(Category::class.java)).let {
            ServerResponse.created(URI("/categories/${it.id}")).body(it)
        }
    }

    fun update(request: ServerRequest): ServerResponse {
        categoryRepository.updateOne(request.pathVariable("id"), request.body(Category::class.java)).let {
            val category = (it ?: return ServerResponse.notFound().build())
            return ServerResponse.ok().body(category)
        }
    }

    fun delete(request: ServerRequest): ServerResponse = categoryRepository.deleteOne(request.pathVariable("id")).let {
        ServerResponse.noContent().build()
    }
}