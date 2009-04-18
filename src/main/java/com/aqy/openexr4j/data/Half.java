/**
 * Copyright (c) 2009, A.Q.Yang.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are 
 * met:
 *
 *    * Redistributions of source code must retain the above 
 *      copyright notice, this list of conditions and the following 
 *      disclaimer.
 *      
 *    * Redistributions in binary form must reproduce the above 
 *      copyright notice, this list of conditions and the following 
 *      disclaimer in the documentation and/or other materials provided 
 *      with the distribution.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS 
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package com.aqy.openexr4j.data;

import org.apache.log4j.Logger;

/**
 * Half.java<br>
 * <br>
 * Representation of a float:<br>
 * We assume that a float, f, is an IEEE 754 single-precision 
 * floating point number, whose bits are arranged as follows:<br>
 * <br>
 * <table>
 * <tr>
 * <td>31(msb)</td><td>30&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;23</td><td>22&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0(lsb)</td>
 * </tr>
 * <tr>
 * <td>|</td><td>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</td><td>|&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;|</td>
 * </tr>
 * <tr>
 * <td>X</td><td>XXXXXXXX</td><td>XXXXXXXXXXXXXXXXXXXXXXX</td>
 * </tr>
 * <tr>
 * <td>s</td><td>e</td><td>m</td>
 * </tr>
 * </table>
 * <br>
 * s is the sign-bit, e is the exponent and m is the significand.<br>
 * <br>
 * If e is between 1 and 254, f is a normalized number:<br>
 * <br>
 * f = (-1)<sup>s</sup> * 2<sup>e-127</sup> * 1.m<br>
 * <br>
 * If e is 0, and m is not zero, f is a denormalized number:<br>
 * <br>
 * f = (-1)<sup>s</sup> * 2<sup>-126</sup> * 0.m<br>
 * <br>
 * If e and m are both zero, f is zero:<br>
 * <br>
 * f = 0.0<br>
 * <br>
 * <br>If e is 255, f is an "infinity" or "not a number" (NAN), depending on 
 * whether m is zero or not.<br>
 * <br>
 * Examples:<br>
 * <br>
 * 0 00000000 00000000000000000000000 = 0.0<br>
 * 0 01111110 00000000000000000000000 = 0.5<br>
 * 0 01111111 00000000000000000000000 = 1.0<br>
 * 0 10000000 00000000000000000000000 = 2.0<br>
 * 0 10000000 10000000000000000000000 = 3.0<br>
 * 1 10000101 11110000010000000000000 = -124.0625<br>
 * 0 11111111 00000000000000000000000 = +infinity<br>
 * 1 11111111 00000000000000000000000 = -infinity<br>
 * 0 11111111 10000000000000000000000 = NAN<br>
 * 1 11111111 11111111111111111111111 = NAN<br>
 * <br>
 * Representation of a half:<br>
 * <br>
 * Here is the bit-layout for a half number, h:<br>
 * <br>
 * <table>
 * <tr>
 * <td>15(msb)</td><td>14&nbsp;&nbsp;&nbsp;10</td><td>9&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0(lsb)</td>
 * </tr>
 * <tr>
 * <td>|</td><td>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</td>
 * <td>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</td>
 * </tr>
 * <tr>
 * <td>X</td><td>XXXXX</td><td>XXXXXXXXXX</td>
 * </tr>
 * <tr>
 * <td>s</td><td>e</td><td>m</td>
 * </tr>
 * </table>
 * <br>
 * s is the sign-bit, e is the exponent and m is the significand.<br>
 * <br>
 * <br>If e is between 1 and 30, h is a normalized number:<br>
 * <br>
 * h = (-1)<sup>s</sup> * 2<sup>e-15</sup> * 1.m<br>
 * <br>
 * If e is 0, and m is not zero, h is a denormalized number:<br>
 * <br>
 * h = (-1)<sup>s</sup>  * 2<sup>-14</sup> * 0.m<br>
 * <br>
 * If e and m are both zero, h is zero:<br>
 * <br>
 * h = 0.0<br>
 * <br>
 * If e is 31, h is an "infinity" or "not a number" (NAN), depending 
 * on whether m is zero or not.<br>
 * <br>
 * Examples:<br>
 * <br>
 * 0 00000 0000000000 = 0.0<br>
 * 0 01110 0000000000 = 0.5<br>
 * 0 01111 0000000000 = 1.0<br>
 * 0 10000 0000000000 = 2.0<br>
 * 0 10000 1000000000 = 3.0<br>
 * 1 10101 1111000001 = -124.0625<br>
 * 0 11111 0000000000 = +infinity<br>
 * 1 11111 0000000000 = -infinity<br>
 * 0 11111 1000000000 = NAN<br>
 * 1 11111 1111111111 = NAN<br>
 * <br>
 * Conversion:<br>
 * <br>
 * Converting from a float to a half requires some non-trivial bit
 * manipulations.  In some cases, this makes conversion relatively slow, 
 * but the most common case is accelerated via table lookups.<br>
 * <br>
 * Converting back from a half to a float is easier because we don't
 * have to do any rounding. In addition, there are only 65536 
 * different half numbers; we can convert each of those numbers once 
 * and store the results in a table.  Later, all conversions can be 
 * done using only simple table lookups.
 *
 * 
 * @author alfred.qyang@gmail.com
 * @since 2009-04-07
 * @version $Id$
 * 
 */
public class Half extends Number implements FP16Bit, Comparable<FP16Bit> {
	
	private static final Logger log = Logger.getLogger(Half.class);
	 /**
     * A constant holding the positive infinity of type
     * <code>Half</code>. It is equal to the value returned by
     * <code>Float.intBitsToFloat(0x7f800000)</code>.
     */
    public static final Half POSITIVE_INFINITY = new Half((short)0x7c00);

    /**
     * A constant holding the negative infinity of type
     * <code>Half</code>. It is equal to the value returned by
     * <code>Float.intBitsToFloat(0xff800000)</code>.
     */
    public static final Half NEGATIVE_INFINITY = new Half((short)0xfc00);

    /** 
     * A constant holding a Not-a-Number (NaN) value of type
     * <code>Half</code>.  It is equivalent to the value returned by
     * <code>Float.intBitsToFloat(0x7fc00000)</code>.
     */
    public static final Half NaN = new Half((short)0x7e00);
    
    /**
     * the smallest positive half, 2<sup>-24</sup>
     * 
     * */
    public static final Half HALF_MIN = new Half((short)0x0001);
    
    /**
     * the smallest positive normalized half, 2<sup>-14</sup>
     * 
     * */
    public static final Half HALF_NORMALIZED_MIN = new Half((short)0x0800);
    
    /**
     * the largest positive half, (2 - 2<sup>-10</sup>) x 2<sup>15</sup>
     * 
     * */
    public static final Half HALF_MAX = new Half((short)0x7fff);
    
    /**
     * the smallest positive e which (1.0 + e) != 1.0, 2<sup>-10</sup>
     * 
     * */
    public static final Half HALF_EPSILON = new Half((short)0x3c01);

	private short half;
	
	public Half(short half) {
		this.half = half;
	}

	public Half(float f) {
		setupHalf(f);
	}
	
	private void setupHalf(Float f) {
		int rawIntBits = Float.floatToRawIntBits(f);
		if(f == 0) {
			// Common special case - zero.
			// Preserve the zero's sign bit.
			half = (short)(rawIntBits >> 16);
		} else {
			// We extract the combined sign and exponent, se, from our
			// floating-point number, f.  Then we convert se to the sign
			// and exponent of the half number via a table lookup.

			// For the most common case, where a normalized half is produced,
			// the table lookup returns a non-zero value; in this case, all
			// we have to do is round f's significand to 10 bits and combine
			// the result with e.

			// For all other cases (overflow, zeroes, denormalized numbers
			// resulting from underflow, infinities and NANs), the table
			// lookup returns zero, and we call a longer, non-inline function
			// to do the float-to-half conversion.
			int e = (rawIntBits >> 23) & 0x1ff;
			e = HalfsLUT.getInstance().get16BitSE(e);
			if(e != 0) {
				// simple case - round the significand, m, to 10
				// bits and combine it with the sign and exponent.
				int m = rawIntBits & 0x7fffff;
				half = (short)(e + HalfUtils.roundToNearestEven(m, 13));
			} else 	{
				// difficult case - call a function.
				half = HalfUtils.toFP16BitRawBits(rawIntBits);
			}
			log.trace(String.format("Constructed half [0x%x(16Bit)] by float [0x%x(32Bit)].", half, rawIntBits));
		}
	}
	
	public static short halfToRawShortBits(Half half) {
		return half.half;
	}
	
	@Override
	public float floatValue() {
		return HalfsLUT.getInstance().get32BitFloat(half);
	}

	@Override
	public double doubleValue() {
		return (double)floatValue();
	}

	@Override
	public int intValue() {
		return (int)floatValue();
	}

	@Override
	public long longValue() {
		return (long)floatValue();
	}

	@Override
	public FP16Bit add(Float f) {
		return new Half(floatValue() + f);
	}

	@Override
	public void addAndSet(Float f) {
		setupHalf(floatValue() + f); 
	}

	@Override
	public FP16Bit div(Float f) {
		return new Half(floatValue() / f);
	}

	@Override
	public void divAndSet(Float f) {
		setupHalf(floatValue() / f);
	}

	@Override
	public boolean equals(Float f) {
		return Float.compare(floatValue(), f) == 0;
	}

	@Override
	public boolean greaterThan(Float f) {
		return Float.compare(floatValue(), f) > 0;
	}

	@Override
	public boolean lessThan(Float f) {
		return Float.compare(floatValue(), f) < 0;
	}

	@Override
	public FP16Bit mul(Float f) {
		return new Half(floatValue() * f);
	}

	@Override
	public void mulAndSet(Float f) {
		setupHalf(floatValue() * f);
	}

	@Override
	public FP16Bit sub(Float f) {
		Half h = new Half(floatValue() - f);
		return h;
	}

	@Override
	public void subAndSet(Float f) {
		setupHalf(floatValue() - f);
	}
	
	@Override
	public FP16Bit negate() {
		Half h = new Half((short)(half ^ 0x8000));
		return h;
	}

	@Override
	public int compareTo(FP16Bit f) {
		return Float.compare(floatValue(), f.floatValue());
	}

	@Override
	public FP16Bit round(int n) {
		// Parameter check.
		if (n >= 10) {
			return this;
		}

		// disassemble h into the sign, s,
		// and the combined exponent and significand, e.
		short s = (short)(half & 0x8000);
		short e = (short)(half & 0x7fff);

		// Round the exponent and significand to the nearest value
		// where ones occur only in the (10-n) most significant bits.
		// Note that the exponent adjusts automatically if rounding
		// up causes the significand to overflow.
		e >>= 9 - n;
		e  += e & 1;
		e <<= 9 - n;

		// Check for exponent overflow.
		if(e >= 0x7c00) {
			// Overflow occurred -- truncate instead of rounding.
			e = half;
			e >>= 10 - n;
			e <<= 10 - n;
		}

		// Put the original sign bit back.
		Half h = new Half((short)(s | e));

		return h;
	}

	@Override
	public boolean isDenormalized() {
		int e = (half >> 10) & 0x001f;
		int m =  half & 0x3ff;
		return e == 0 && m != 0;
	}

	@Override
	public boolean isFinite() {
		int e = (half >> 10) & 0x001f;
		return e < 31;
	}

	@Override
	public boolean isInfinity() {
		int e = (half >> 10) & 0x001f;
		int m =  half & 0x3ff;
		return e == 31 && m == 0;
	}

	@Override
	public boolean isNaN() {
		int e = (half >> 10) & 0x001f;
		int m =  half & 0x3ff;
		return e == 31 && m != 0;
	}

	@Override
	public boolean isNegative() {
		return (half & 0x8000) != 0;
	}

	@Override
	public boolean isNormalized() {
		int e = (half >> 10) & 0x001f;
		return e > 0 && e < 31;
	}

	@Override
	public boolean isZero() {
		return (half & 0x7fff) == 0;
	}

	private static final long serialVersionUID = 415178403768505905L;
}
