functional-matrix-operator
========

functional-matrix-operator is a library for matrix operation in form of lazy evaluation.
It supports matrix operations(Multiply, Divide, Minus, Plus, Transpose).

 - Basic Featurs
   - immutable : basically all of matrix values are immutable. you can use concurrent operation with matrix.
   - funtional style : all of transforming operations are occured in funtional evaluation.
   - lazy evaluation : your matrix values are not invoked until `invoke()` method is called.
   - transforming (head, tail, init, last) : you can easily get a abstract subset of the matrix. it reduces overhead of  copy operation.

How to use
========
 - Support java version : 7

Transforming
========
 - Next
 - Under
   - you can do the following:
```java
double[] sample = new double[]{ 1l, 2l, 3l, 4l, 5l, 6l };
DoubleOperator head = DoubleOperator.of(1l);
DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
```
result :
```
1.0,1.0,2.0
1.0,3.0,4.0
1.0,5.0,6.0
```

Subset
========
 - Head 
 - Tail
 - Init
 - Last
   - you can do the following:
```java
double[] sample = new double[]{ 1l, 2l, 3l, 4l, 5l, 6l };
DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
System.out.println(one.head());
```
result :
```
1.0
3.0
5.0
```
 
Operations
========
 - Multiply
 - Minus
 - Plus
 - Divide
 - Traspose
   - you can do the following:
   
```java
double[] sample = new double[]{ 1l, 2l, 3l, 4l, 5l, 6l };
double[] transpose = new double[]{ 1l, 3l, 5l, 2l, 4l, 6l };

DoubleMatrix one = DenseDoubleMatrix.of(2, sample);
DoubleMatrix other = DenseDoubleMatrix.of(3, transpose);
DoubleMatrix result = one.multiply(other).invoke();
System.out.println(result);
```
result :
```
5.0,11.0,17.0
11.0,25.0,39.0
17.0,39.0,61.0
```
