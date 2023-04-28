package com.example.lab2

import jakarta.servlet.annotation.*
import jakarta.servlet.http.*
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Component
@WebServlet(name = "helloServlet", value = ["/hello-servlet"])
class HelloServlet : HttpServlet() {
    private lateinit var message: String

    override fun init() {
        message = "Hello World!"
    }

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html"

        // Hello
        val out = response.writer
        out.println("<html><body>")
        out.println("<h1>$message</h1>")
        out.println("</body></html>")
    }

    // Сутність Супутник
    data class Satellite(
        val name: String,
        val country: String,
        val launchDate: String,
        val purpose: String,
        val weight: Double,
        val height: Double,
        val geoStationary: Boolean
    ) : Comparable<Satellite> {
        override fun compareTo(other: Satellite): Int {
            return if (this.name == other.name) {
                this.weight.compareTo(other.weight)
            } else {
                this.name.compareTo(other.name)
            }
        }
    }

    // Сутність Процесор
    data class Processor(
        val name: String,
        val manufacturer: String,
        val cores: Int,
        val clockSpeed: Double,
        val socket: String,
        val productionDate: String,
        val mmxSupport: Boolean
    ) : Comparable<Processor> {
        override fun compareTo(other: Processor): Int {
            return if (this.name == other.name) {
                this.cores.compareTo(other.cores)
            } else {
                this.name.compareTo(other.name)
            }
        }
    }

    // Клас-контейнер
    interface Container {
        fun add(item: Any)
        fun remove(index: Int)
        fun update(index: Int, item: Any)
        fun get(index: Int): Any
        fun getAll(): List<Any>
    }

    @Component
    class ContainerImpl : Container {
        private val list = mutableListOf<Any>()

        override fun add(item: Any) {
            list.add(item)
        }

        override fun remove(index: Int) {
            list.removeAt(index)
        }

        override fun update(index: Int, item: Any) {
            list[index] = item
        }

        override fun get(index: Int): Any {
            return list[index]
        }

        override fun getAll(): List<Any> {
            return list
        }
    }

    // Файл основної програми Main.kt
    fun main() {
        val context = AnnotationConfigApplicationContext()
        context.register(ContainerImpl::class.java)
        context.refresh()

        val container = context.getBean(Container::class.java)

        // Створення 5 різноманітних основних сутностей
        val satellite1 = Satellite("Satellite 1", "USA", "01.01.2020", "Communication", 1000.0, 35786.0, true)
        val satellite2 = Satellite("Satellite 2", "Ukraine", "02.02.2020", "Navigation", 2000.0, 35786.0, false)
        val satellite3 = Satellite("Satellite 3", "China", "03.03.2020", "Weather", 3000.0, 35786.0, true)
        val satellite4 = Satellite("Satellite 4", "Japan", "04.04.2020", "Earth Observation", 4000.0, 35786.0, false)
        val satellite5 = Satellite("Satellite 5", "India", "05.05.2020", "Communication", 5000.0, 35786.0, true)

        // Додавання сутностей в список
        container.add(satellite1)
        container.add(satellite2)
        container.add(satellite3)
        container.add(satellite4)
        container.add(satellite5)

        // Отримання сутності з індексом 3
        val satellite3 = container.get(3) as Satellite

        // Видалення сутності з індексом 4
        container.remove(4)
    }

    override fun destroy() {
    }
}