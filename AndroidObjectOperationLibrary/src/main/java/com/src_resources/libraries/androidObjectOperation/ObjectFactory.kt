package com.src_resources.libraries.androidObjectOperation

/**
 * 对象工厂类。
 * 使用此类可以更便捷地提供一个类型创建对象的方式。
 */
class ObjectFactory<out T> private constructor(
        // 对象获取函数。
        private var mObjectObtainingFunction: (ObjectFactory<T>) -> T) {

    companion object {
        /**
         * 使用指定的对象获取函数实例化一个 ObjectFactory 对象。
         * @param mObjectObtainingFunction 对象获取函数，用于在 ObjectFactory 实例化对象时回调。
         * @return 实例化好了的 ObjectFactory 对象。
         */
        fun <T> create(mObjectObtainingFunction: (ObjectFactory<T>) -> T): ObjectFactory<T> {
            // 根据该对象获取函数，创建并返回 ObjectFactory 对象。
            return ObjectFactory(mObjectObtainingFunction)
        }
        /**
         * 使用指定的 ObjectFactoryCallback 对象实例化一个 ObjectFactory 对象。
         * @param mObjectFactoryCallback ObjectFactoryCallback 对象，用于在 ObjectFactory 实例化对象时回调。
         * @return 实例化好了的 ObjectFactory 对象。
         */
        fun <T> create(mObjectFactoryCallback: ObjectFactoryCallback<T>): ObjectFactory<T> {
            // 根据该 ObjectFactoryCallback 对象，创建并返回 ObjectFactory 对象。
            // 实现原理是创建一个 lambda 表达式，在表达式中调用 ObjectFactoryCallback 的 obtainObject() 方法，
            // 并使用这个 lambda 表达式调用上面的 obtain() 函数，并返回 obtain 到的 ObjectFactory 对象。
            return create({ mObjectFactoryCallback.createObject(it) })
        }
    }

    /**
     * 使用该 ObjectFactory 实例化一个对象。
     * @return 实例化好了的对象。
     */
    fun createObject(): T {
        // 调用对应的对象获取函数，获取对象。
        return mObjectObtainingFunction(this)
    }
}
