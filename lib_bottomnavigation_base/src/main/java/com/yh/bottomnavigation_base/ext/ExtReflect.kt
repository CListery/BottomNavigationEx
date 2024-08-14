package com.yh.bottomnavigation_base.ext

import android.os.Build
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

private fun Field?.editModifiers(modifiers: Int) {
    if(modifiers == -1) {
        return
    }
    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
        val accessFlagsField = Field::class.java.getDeclaredField("accessFlags")
        accessFlagsField.isAccessible = true
        accessFlagsField.setInt(this, modifiers)
    } else {
        val artField = Field::class.java.getDeclaredField("artField")
        artField.isAccessible = true
        val artF = artField.get(this)
        val artFieldClazz = Class.forName("java.lang.reflect.ArtField")
        val accessFlagsField = artFieldClazz.getDeclaredField("accessFlags")
        accessFlagsField.isAccessible = true
        accessFlagsField.setInt(artF, modifiers)
    }
}

/**
 * 安全访问
 *
 * @param [block] 访问回调
 */
fun <T : AccessibleObject, R : Any> T.safeAccess(ignoreFinal: Boolean = true, block: (T) -> R?): R? {
    var result: R? = null
    try {
        val originAccessible = isAccessible
        var modifiers: Int = -1
        isAccessible = true
        if(ignoreFinal) {
            if(this is Field) {
                modifiers = this.modifiers
                if(Modifier.isFinal(modifiers)) {
                    this.editModifiers(modifiers.xor(Modifier.FINAL))
                }
            }
        }
        result = block.invoke(this)
        if(ignoreFinal) {
            if(this is Field) {
                this.editModifiers(modifiers)
            }
        }
        isAccessible = originAccessible
    } catch(e: Exception) {
        e.printStackTrace()
    }
    return result
}

private fun Class<*>.findField(fieldName: String): Field? {
    val field = declaredFields.find { it.name == fieldName }
    if(null == field) {
        if(superclass == Any::class.java) {
            return null
        }
        return superclass.findField(fieldName)
    } else {
        return field
    }
}

private fun <T : Any> T.getField(fieldName: String): Field {
    return this::class.java.findField(fieldName)
        ?: throw Exception("not found $fieldName in ${this::class.java}")
}

private fun Class<*>.findFun(funName: String): Method? {
    val method = declaredMethods.find { it.name == funName }
    if(null == method) {
        if(superclass == Any::class.java) {
            return null
        }
        return superclass.findFun(funName)
    } else {
        return method
    }
}

private fun <T : Any> T.getFun(funName: String): Method {
    return this::class.java.findFun(funName)
        ?: throw Exception("not found $funName in ${this::class.java}")
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
fun <T : Any, V : Any> T?.setFieldValue(
    fieldName: String,
    value: V?,
): Boolean {
    if(null == this) {
        return false
    }
    getField(fieldName).safeAccess { it.set(this, value) }
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
@Suppress("UNCHECKED_CAST")
fun <T : Any, V : Any> T.getFieldValue(fieldName: String): V {
    return getField(fieldName).safeAccess { it.get(this) } as V
}

@Suppress("UNCHECKED_CAST")
fun <T : Any, V : Any> T.safeGetFieldValue(fieldName: String): V? {
    return getField(fieldName).safeAccess { it.get(this) } as? V
}

@Suppress("UNCHECKED_CAST")
fun <T : Any, V : Any> T?.invokeMethod(funName: String, vararg args: Any?): V? {
    val self = this
        ?: return null
    return getFun(funName).safeAccess { it.invoke(self, args) } as? V
}
