package com.yh.bottomnavigation_base.ext

import android.os.Build
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
    field.isAccessible = true
    val modifiers = field.modifiers
    
    if (Modifier.isFinal(field.modifiers)) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            val accessFlagsField = Field::class.java.getDeclaredField("accessFlags")
            accessFlagsField.isAccessible = true
            accessFlagsField.setInt(field, modifiers.xor(Modifier.FINAL))
        } else {
            val artField = Field::class.java.getDeclaredField("artField")
            artField.isAccessible = true
            val artF = artField.get(field)
            val artFieldClazz = Class.forName("java.lang.reflect.ArtField")
            val accessFlagsField = artFieldClazz.getDeclaredField("accessFlags")
            accessFlagsField.isAccessible = true
            accessFlagsField.setInt(artF, modifiers.xor(Modifier.FINAL))
        }
    }
    field.set(this, value)
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
    field.isAccessible = true
    val result = field.get(this)
    @Suppress("UNCHECKED_CAST")
    return result as V
}