package com.felix.labs.category.model

class Category {

    constructor() {
    }

    constructor(name: String, description: String, active: Boolean) {
        this.name = name
        this.description = description
        this.active = active
    }

    var id: String = ""
        private set

    var name: String = ""
        private set

    var description: String = ""
        private set

    var active: Boolean? = null
        private set

    fun update(name: String, description: String, active: Boolean?) {
        this.name    = name
        this.description = description
        this.active  = active
    }
}