package com.github.soursop.matrix.operator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class MinusInvoker implements InvocationHandler {
    public static Operators of(Operators delegate) {
        return (Operators) (Proxy.newProxyInstance(
                delegate.getClass().getClassLoader(),
                new Class[] { Operators.class },
                new MinusInvoker(delegate)));
    }

    private final Operators delegate;
    public MinusInvoker(Operators delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        Object invoke = method.invoke(delegate, args);
        if (method.getName().equals("minus") && method.getParameterTypes().length == 0 && Operator.class.isAssignableFrom(returnType)) {
            return delegate;
        }
        if (DoubleMatrix.class.isAssignableFrom(returnType)) {
            return new MinusDoubleMatrix<>((DoubleMatrix) invoke);
        }
        return invoke;
    }
}
