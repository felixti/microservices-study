package com.felix.labs.category

import com.felix.labs.category.data.CategoryRepository
import com.felix.labs.category.model.Category
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import java.net.URI

@SpringBootApplication
class CategoryApplication

fun beans() = beans {
    bean {
        router {
            "/api/categories".nest {
                val handler = CategoryHandler(ref())
                GET("/", handler::readAll)
                GET("/{id}", handler::readOne)
                POST("/", handler::create)
                PUT("/{id}", handler::update)
                DELETE("/{id}", handler::delete)
            }
        }
    }
}

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

fun main(args: Array<String>) {
    runApplication<CategoryApplication>(*args) {
        addInitializers(beans())
    }
}