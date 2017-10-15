package com.securityinnovation.jNeo.math;

/**
 * Defines an interface for finding the inverse of a polynomial. Different
 * implementations of this interface will be optimized for different moduli,
 * this is why the modulus is not part of this interface.
 */
public interface PolynomialInverter {

    /**
     * Calculate the inverse of a polynomial.
     *
     * @param a
     * @return
     */
    public abstract FullPolynomial invert(
            FullPolynomial a);
}
