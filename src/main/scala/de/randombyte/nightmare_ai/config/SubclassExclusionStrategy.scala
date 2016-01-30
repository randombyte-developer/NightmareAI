package de.randombyte.nightmare_ai.config

import java.lang.reflect.Field

import com.google.gson.{ExclusionStrategy, FieldAttributes}

/**
  * If a Field was also found in one of the superclasses then the field of the sublass will be excluded
  */
class SubclassExclusionStrategy extends ExclusionStrategy {

  override def shouldSkipClass(clazz: Class[_]): Boolean = false

  override def shouldSkipField(fieldAttributes: FieldAttributes): Boolean =
    isFieldInSuperclass(fieldAttributes.getDeclaringClass.getSuperclass, fieldAttributes.getName)

  /**
    * Tries to find the fieldName in superclass and its superclasses
    * @param superclass The superclass of the original class
    * @param fieldName The name of the field to find
    * @return true if field was found, false if not
    */
  def isFieldInSuperclass(superclass: Class[_], fieldName: String): Boolean = {
    if (superclass != null) {
      if (getField(superclass, fieldName) != null) true
      else isFieldInSuperclass(superclass.getSuperclass, fieldName)
    } else false
  }

  def getField(clazz: Class[_], fieldName: String): Field = {
    try {
      clazz.getDeclaredField(fieldName)
    } catch {
      case nsfError: NoSuchFieldException => null
    }
  }
}
