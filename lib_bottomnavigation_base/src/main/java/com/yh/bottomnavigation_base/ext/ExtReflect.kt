package com.yh.bottomnavigation_base.ext

import java.lang.reflect.Field
import java.lang.reflect.Modifier

private fun Class<*>.findField(fieldName: String): Field? {
    val field = declaredFields.find { it.name == fieldName }
    if (null == field) {
        if (superclass == Any::class.java) {
            return null
        }
        return superclass.findField(fieldName)
    } else {
        return field
    }
}

/**
 * change the field value
 *
 * @param T    the filed owner
 * @param fieldName
 * @param value
 * @param <T>  owner type
 * @param <V>  value type
 * @return change result
 */
fun <T : Any, V : Any> T?.setField(
    fieldName: String,
    value: V,
): Boolean {
    if (null == this) {
        return false
    }
    val field = this::class.java.findField(fieldName)
        ?: throw Exception("not found $fieldName in ${this::class.java}")
    val accessible = field.isAccessible
    field.isAccessible = true
    val modifiers = field.modifiers
    
    val accessFlags = Field::class.java.getDeclaredField("accessFlags")
    val accessible1 = accessFlags.isAccessible
    accessFlags.isAccessible = true
    accessFlags.setInt(field, modifiers.xor(Modifier.FINAL))
    
    field.set(this, value)
    
    accessFlags.setInt(field, modifiers)
    accessFlags.isAccessible = accessible1
    
    field.isAccessible = accessible
    return true
}

/**
 * get private filed in this specific object
 *
 * @param T    the filed owner
 * @param fieldName
 * @param <T>  owner type
 * @param <V>  result type
 * @return field if success, null otherwise.
 */
fun <T : Any, V : Any> T.getField(fieldName: String): V {
    val field = this::class.java.findField(fieldName)
        ?: throw Exception("not found $fieldName in ${this::class.java}")
    val accessible = field.isAccessible
    field.isAccessible = true
    val result = field.get(this)
    field.isAccessible = accessible
    @Suppress("UNCHECKED_CAST")
    return result as V
}