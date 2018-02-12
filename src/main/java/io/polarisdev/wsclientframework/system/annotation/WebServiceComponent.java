package io.polarisdev.wsclientframework.system.annotation;

@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@org.springframework.stereotype.Component
public @interface WebServiceComponent {
  String serviceName();

  String transformer();

  String serviceClient();

  String type() default "RestFull";

  String fallbackService() default "";

  java.lang.String value() default "";
}
