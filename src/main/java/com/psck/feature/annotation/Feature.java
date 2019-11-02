package com.psck.feature.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Feature {
  @AliasFor("value")
  String name() default "";

  @AliasFor("name")
  String value() default "";

  String description() default "";
}
