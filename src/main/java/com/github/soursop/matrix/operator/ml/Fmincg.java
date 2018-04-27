package com.github.soursop.matrix.operator.ml;

import com.github.soursop.matrix.operator.DoubleMatrix;
import com.github.soursop.matrix.operator.DoubleOperator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Minimize a continuous differentialble multivariate function. Starting point <br/>
 * is given by "X" (D by 1), and the function named in the string "f", must<br/>
 * return a function value and a vector of partial derivatives. The Polack-<br/>
 * Ribiere flavour of conjugate gradients is used to compute search directions,<br/>
 * and a line search using quadratic and cubic polynomial approximations and the<br/>
 * Wolfe-Powell stopping criteria is used together with the slope ratio method<br/>
 * for guessing initial step sizes. Additionally a bunch of checks are made to<br/>
 * make sure that exploration is taking place and that extrapolation will not<br/>
 * be unboundedly large. The "length" gives the length of the run: if it is<br/>
 * positive, it gives the maximum number of line searches, if negative its<br/>
 * absolute gives the maximum allowed number of function evaluations. You can<br/>
 * (optionally) give "length" a second component, which will indicate the<br/>
 * reduction in function value to be expected in the first line-search (defaults<br/>
 * to 1.0). The function returns when either its length is up, or if no further<br/>
 * progress can be made (ie, we are at a minimum, or so close that due to<br/>
 * numerical problems, we cannot get any closer). If the function terminates<br/>
 * within a few iterations, it could be an indication that the function value<br/>
 * and derivatives are not consistent (ie, there may be a bug in the<br/>
 * implementation of your "f" function). The function returns the found<br/>
 * solution "X", a vector of function values "fX" indicating the progress made<br/>
 * and "i" the number of iterations (line searches or function evaluations,<br/>
 * depending on the sign of "length") used.<br/>
 * <br/>
 * Usage: [X, fX, i] = fmincg(f, X, options, P1, P2, P3, P4, P5)<br/>
 * <br/>
 * See also: checkgrad <br/>
 * <br/>
 * Copyright (C) 2001 and 2002 by Carl Edward Rasmussen. Date 2002-02-13<br/>
 * <br/>
 * <br/>
 * (C) Copyright 1999, 2000 & 2001, Carl Edward Rasmussen <br/>
 * Permission is granted for anyone to copy, use, or modify these<br/>
 * programs and accompanying documents for purposes of research or<br/>
 * education, provided this copyright notice is retained, and note is<br/>
 * made of any changes that have been made.<br/>
 * <br/>
 * These programs and documents are distributed without any warranty,<br/>
 * express or implied. As the programs were written for research<br/>
 * purposes only, they have not been tested to the degree that would be<br/>
 * advisable in any important application. All use of these programs is<br/>
 * entirely at the user's own risk.<br/>
 * <br/>
 * [ml-class] Changes Made:<br/>
 * 1) Function name and argument specifications<br/>
 * 2) Output display<br/>
 * <br/>
 * [tjungblut] Changes Made: <br/>
 * 1) translated from octave to java<br/>
 * 2) added an interface to exchange minimizers more easily <br/>
 * 3) in preparation for the c++ translation, I removed unused fields<br/>
 * BTW "fmincg" stands for Function minimize nonlinear conjugate gradient
 */
public class Fmincg extends AbstractMinimizer {
    private static final Log LOG = LogFactory.getLog(Fmincg.class);

    // extrapolate maximum 3 times the current bracket.
    // this can be set higher for bigger extrapolations
    public static double EXT = 3.0;

    // a bunch of constants for line searches
    private static final double RHO = 0.01;
    // RHO and SIG are the constants in the Wolfe-Powell conditions
    private static final double SIG = 0.5;
    // don't reevaluate within 0.1 of the limit of the current bracket
    private static final double INT = 0.1;
    // max 20 function evaluations per line search
    private static final int MAX = 20;
    // maximum allowed slope ratio
    private static final int RATIO = 100;

    private static final DoubleOperator MINUS = DoubleOperator.of(-1.0d);

    /**
     * Minimizes the given Derivative with Nonlinear conjugate gradient method. <br/>
     * It uses the Polack-Ribiere (PR) to calculate the conjugate direction. See <br/>
     * {@link http://en.wikipedia.org/wiki/Nonlinear_conjugate_gradient_method} <br/>
     * for more information.
     *
     * @param f the cost function to minimize.
     * @param theta the input vector, also called starting point
     * @param maxIterations the number of iterations to make
     * @return a vector containing the optimized input
     */
    public static DoubleMatrix asMinimize(Gradient f, DoubleMatrix theta, int maxIterations) {
        return new Fmincg().minimize(f, theta, maxIterations);
    }

    @Override
    public final DoubleMatrix minimize(Gradient f, DoubleMatrix theta,
                                       int length) {

        DoubleMatrix input = theta;
        int M = 0;
        int i = 0; // zero the run length counter
        int red = 1; // starting point
        int ls_failed = 0; // no previous line search has failed
        final Cost evaluateCost = f.gradient(input);
        double f1 = evaluateCost.cost();
        DoubleMatrix df1 = evaluateCost.theta();
        i = i + (length < 0 ? 1 : 0);
        // search direction is steepest
        DoubleMatrix s = df1.multiply(MINUS).invoke();

        double d1 = s.multiply(MINUS).dot(s).getValue(); // this is the slope
        double z1 = red / (1.0 - d1); // initial step is red/(|s|+1)

        while (i < Math.abs(length)) {// while not finished
            i = i + (length > 0 ? 1 : 0);// count iterations?!
            // make a copy of current values
            DoubleMatrix X0 = input.copy();
            double f0 = f1;
            DoubleMatrix df0 = df1.copy();
            // begin line search
            input = input.plus(s.multiply(DoubleOperator.of(z1))).invoke();
            final Cost evaluateCost2 = f.gradient(input);
            double f2 = evaluateCost2.cost();
            DoubleMatrix df2 = evaluateCost2.theta();

            i = i + (length < 0 ? 1 : 0); // count epochs
            double d2 = df2.dot(s).getValue();
            // initialize point 3 equal to point 1
            double f3 = f1;
            double d3 = d1;
            double z3 = -z1;
            if (length > 0) {
                M = MAX;
            } else {
                M = Math.min(MAX, -length - i);
            }
            // initialize quanteties
            int success = 0;
            double limit = -1;

            while (true) {
                while (((f2 > f1 + z1 * RHO * d1) | (d2 > -SIG * d1)) && (M > 0)) {
                    // tighten the bracket
                    limit = z1;
                    double z2 = 0.0d;
                    double A = 0.0d;
                    double B = 0.0d;
                    if (f2 > f1) {
                        // quadratic fit
                        z2 = z3 - (0.5 * d3 * z3 * z3) / (d3 * z3 + f2 - f3);
                    } else {
                        // cubic fit
                        A = 6 * (f2 - f3) / z3 + 3 * (d2 + d3);
                        B = 3 * (f3 - f2) - z3 * (d3 + 2 * d2);
                        // numerical error possible - ok!
                        z2 = (Math.sqrt(B * B - A * d2 * z3 * z3) - B) / A;
                    }
                    if (Double.isNaN(z2) || Double.isInfinite(z2)) {
                        // if we had a numerical problem then bisect
                        z2 = z3 / 2.0d;
                    }
                    // don't accept too close to limits
                    z2 = Math.max(Math.min(z2, INT * z3), (1 - INT) * z3);
                    // update the step
                    z1 = z1 + z2;
                    input = input.plus(s.multiply(DoubleOperator.of(z2))).invoke();
                    final Cost evaluateCost3 = f.gradient(input);
                    f2 = evaluateCost3.cost();
                    df2 = evaluateCost3.theta();
                    M = M - 1;
                    i = i + (length < 0 ? 1 : 0); // count epochs
                    d2 = df2.dot(s).getValue();
                    // z3 is now relative to the location of z2
                    z3 = z3 - z2;
                }
                if (f2 > f1 + z1 * RHO * d1 || d2 > -SIG * d1) {
                    break; // this is a failure
                } else if (d2 > SIG * d1) {
                    success = 1;
                    break; // success
                } else if (M == 0) {
                    break; // failure
                }
                // make cubic extrapolation
                double A = 6 * (f2 - f3) / z3 + 3 * (d2 + d3);
                double B = 3 * (f3 - f2) - z3 * (d3 + 2 * d2);
                double z2 = -d2 * z3 * z3 / (B + Math.sqrt(B * B - A * d2 * z3 * z3));
                // num prob or wrong sign?
                if (Double.isNaN(z2) || Double.isInfinite(z2) || z2 < 0)
                    // if we have no upper limit
                    if (limit < -0.5) {
                        // the extrapolate the maximum amount
                        z2 = z1 * (EXT - 1);
                    } else {
                        // otherwise bisect
                        z2 = (limit - z1) / 2;
                    }
                else if ((limit > -0.5) && (z2 + z1 > limit)) {
                    // extraplation beyond max?
                    z2 = (limit - z1) / 2; // bisect
                } else if ((limit < -0.5) && (z2 + z1 > z1 * EXT)) {
                    // extrapolationbeyond limit
                    z2 = z1 * (EXT - 1.0); // set to extrapolation limit
                } else if (z2 < -z3 * INT) {
                    z2 = -z3 * INT;
                } else if ((limit > -0.5) && (z2 < (limit - z1) * (1.0 - INT))) {
                    // too close to the limit
                    z2 = (limit - z1) * (1.0 - INT);
                }
                // set point 3 equal to point 2
                f3 = f2;
                d3 = d2;
                z3 = -z2;
                z1 = z1 + z2;
                // update current estimates
                input = input.plus(s.multiply(DoubleOperator.of(z2))).invoke();
                final Cost evaluateCost3 = f.gradient(input);
                f2 = evaluateCost3.cost();
                df2 = evaluateCost3.theta();
                M = M - 1;
                i = i + (length < 0 ? 1 : 0); // count epochs?!
                d2 = df2.dot(s).getValue();
            }// end of line search

            DoubleMatrix tmp = null;

            if (success == 1) { // if line search succeeded
                f1 = f2;
                if (LOG.isInfoEnabled()) {
                    LOG.info("Iteration " + i + " | Cost: " + f1);
                }
                onFinished(i, f1, input);

                // Polack-Ribiere direction: s =
                // (df2'*df2-df1'*df2)/(df1'*df1)*s - df2;
                final double numerator = (df2.dot(df2).getValue() - df1.dot(df2).getValue()) / df1.dot(df1).getValue();
                s = s.multiply(DoubleOperator.of(numerator)).minus(df2).invoke();
                tmp = df1;
                df1 = df2;
                df2 = tmp; // swap derivatives
                d2 = df1.dot(s).getValue();
                if (d2 > 0) { // new slope must be negative
                    s = df1.multiply(MINUS).invoke(); // otherwise use steepest direction
                    d2 = s.multiply(MINUS).dot(s).getValue();
                }
                // realmin in octave = 2.2251e-308
                // slope ratio but max RATIO
                z1 = z1 * Math.min(RATIO, d1 / (d2 - 2.2251e-308));
                d1 = d2;
                ls_failed = 0; // this line search did not fail
            } else {
                input = X0;
                f1 = f0;
                df1 = df0; // restore point from before failed line search
                // line search failed twice in a row?
                if (ls_failed == 1 || i > Math.abs(length)) {
                    break; // or we ran out of time, so we give up
                }
                tmp = df1;
                df1 = df2;
                df2 = tmp; // swap derivatives
                s = df1.multiply(MINUS).invoke(); // try steepest
                d1 = s.multiply(MINUS).dot(s).getValue();
                z1 = 1.0d / (1.0d - d1);
                ls_failed = 1; // this line search failed
            }

        }

        return input;
    }
}
