package com.example.constructionmanagementspring.config.annotation;

import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class RequestResponseBodyMethod extends RequestResponseBodyMethodProcessor {

    private final DtoMapperFactory dtoMapperFactory;

    public <T> T getValue(Object obj, Class<?> parameterType) {
        return (T) obj;
    }

    public RequestResponseBodyMethod(List<HttpMessageConverter<?>> converters, DtoMapperFactory dtoMapperFactory) {
        super(converters);
        this.dtoMapperFactory = dtoMapperFactory;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object o = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        return dtoMapperFactory.createMapper(o.getClass());
    }

    @Override
    protected Object readWithMessageConverters(@NonNull HttpInputMessage inputMessage, MethodParameter parameter, @NonNull Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

        for (Annotation ann : parameter.getParameterAnnotations()) {
            BodyToEntity dtoType = AnnotationUtils.getAnnotation(ann, BodyToEntity.class);
            if (dtoType != null) {
                return super.readWithMessageConverters(inputMessage, parameter, dtoType.value());
            }
        }
        throw new RuntimeException();
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BodyToEntity.class);
    }
}
