package com.github.soursop.matrix.operator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author soursop
 * @created 2018. 4. 3.
 */
public class DivideDoubleMatrixInvoker implements InvocationHandler {
    public static MatrixOperators of(MatrixOperators delegate) {
        return (MatrixOperators) (Proxy.newProxyInstance(
                delegate.getClass().getClassLoader(),
                new Class[] { MatrixOperators.class },
                new DivideDoubleMatrixInvoker(delegate)));
    }

    private final MatrixOperators delegate;
    public DivideDoubleMatrixInvoker(MatrixOperators delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        Object invoke = method.invoke(delegate, args);
        if (DoubleMatrix.class.isAssignableFrom(returnType)) {
            return new DivideDoubleMatrix<>((DoubleMatrix) invoke);
        }
        return invoke;
    }
}
