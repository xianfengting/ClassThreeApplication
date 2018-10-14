package com.src_resources.libraries.androidObjectOperation

import org.junit.Test

class ObjectFactoryTest {
    @Test
    fun test_ObjectFactory() {
        class MyObject {
            fun sayHello() {
                println("Hello!")
            }
        }

        kotlin.run {
            val objectFactoryCallback = object : ObjectFactoryCallback<MyObject> {
                override fun createObject(objectFactory: ObjectFactory<MyObject>) = MyObject()
            }
            val objectFactory = ObjectFactory.create(objectFactoryCallback)
            val myObj = objectFactory.createObject()
            myObj.sayHello()
        }

        kotlin.run {
            val objectFactory = ObjectFactory.create<MyObject> { MyObject() }
            val myObj = objectFactory.createObject()
            myObj.sayHello()
        }
    }
}
