package com.felix.labs.category

import com.felix.labs.category.handlers.CategoryHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.servlet.function.router

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

fun main(args: Array<String>) {
    runApplication<CategoryApplication>(*args) {
        addInitializers(beans())
    }
}