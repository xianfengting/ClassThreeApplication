package com.src_resources.libraries.androidObjectOperation

interface ObjectFactoryCallback<T> {

    /**
     * 实例化并获取一个对象。
     * 当一个 {@link com.src_resources.libraries.objectFactroy.ObjectFactory ObjectFactory} 的 {@link com.src_resources.libraries.objectFactroy.ObjectFactory#obtainObject() obtainObject()} 被调用时，将回调此方法来实例化对象。
     * @param objectFactory 调用该方法的 ObjectFactory 对象。
     * @return 实例化并创建好了的要返回的对象。
     */
    fun createObject(objectFactory: ObjectFactory<T>): T
}