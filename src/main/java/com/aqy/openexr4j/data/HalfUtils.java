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
 * HalfUtils.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-07
 * @version $Id$
 *
 */
public class HalfUtils {
	
	private static final Logger log = Logger.getLogger(HalfUtils.class);
	
	/**
	 * Delta between bias of 32-bit float and 16-bit half float. (127 - 15)
	 * 
	 * */
	public static int BIAS_DELTA = 127 - 15;
	
	/**
	 * Rounds the mantissa with bitsRoundOff
	 * 
	 * @param mantissa mantissa
	 * @param numOfBitsToRoundOff number of bits to be rounded off
	 * @return the result
	 * 
	 * */
	public static int roundToNearestEven(int mantissa, int numOfBitsToRoundOff) {
		assert(numOfBitsToRoundOff < 32);
		int a = 1 << (numOfBitsToRoundOff - 1) - 1;
		int b = (mantissa >> numOfBitsToRoundOff) & 1;
		return (mantissa + a + b) >> numOfBitsToRoundOff;
	}

	/**
	 * Converts the provided raw bits of a 32-bit float to the raw 
	 * bits of a 16-bit half float
	 * 
	 * @param i raw bits of a 32-bit float
	 * @return raw bits of a 16-bit half float
	 * 
	 * */
	public static short toFP16BitRawBits(int i) {
		// Our floating point number, f, is represented by the bit
		// pattern in integer i.  Disassemble that bit pattern into
		// the sign, s, the exponent, e, and the significand, m.
		// Shift s into the position where it will go in in the
		// resulting half number.
		// Adjust e, accounting for the different exponent bias
		// of float and half (127 versus 15).
		int s = (i >> 16) & 0x8000;
		int e = ((i >> 23) & 0xff) - BIAS_DELTA;
		int m =  i & 0x7fffff;

		// reassemble s, e and m into a half:
		if(e <= 0) {
			if(e < -10) {
				// the absolute value of f is less than (2 - pow(2, -10) 
				// x pow(2, -10-15), which is less than HALF_MIN, 
				// therefore, we convert it to a half zero with the 
				// same sign as f.
				return (short)s;
			}

			// E is between -10 and 0.  F is a normalized float
			// whose magnitude is less than HALF_NRM_MIN, pow(2, -14).
			// convert f to a denormalized half

			// add the implicit leading 1 to the significand
			m = m | 0x800000;

			// Round to m to the nearest (10+e)-bit value (with e between
			// -10 and 0); in case of a tie, round to the nearest even value.

			// Rounding might cause the significand to overflow and make
			// our number normalized.  Because of the way a half's bits
			// are laid out, we don't have to treat this case separately;
			// the code below will handle it correctly.
			int t = (23 + 1) - (10 + e);
			m = roundToNearestEven(m, t);

			// assemble the half from s, e (zero) and m
			return (short)(s | m);
		} else if(e == 0xff - BIAS_DELTA) {
			if(m == 0) {
				// f is an infinity; Convert f to a half
				// infinity with the same sign as f
				return (short)(s | 0x7c00);
			} else {
				// f is a NAN; we produce a half NAN that preserves
				// the sign bit and the 10 leftmost bits of the
				// significand of f, with one exception: If the 10
				// leftmost bits are all zero, the NAN would turn 
				// into an infinity, so we have to set at least one
				// bit in the significand.
				m >>= 13;
				return (short)(s | 0x7c00 | m | (m == 0 ? 1 : 0));
			}
		} else {
			// e is greater than zero. f is a normalized float
			// we try to convert f to a normalized half

			// round to m to the nearest 10-bit value  
			// in case of a tie, round to the nearest even value
			m = m + 0xfff + ((m >> 13) & 1);

			if((m & 0x800000) != 0) {
				m = 0;		// overflow in significand,
				e += 1;		// adjust exponent
			}

			// handle exponent overflow
			if(e > 30) {
				log.warn(String.format("Overflow when converting [0x%x] to half raw bits. Infinity returned.", i));
				// if this returns, the half becomes an
				// infinity with the same sign as f.
				return (short)(s | 0x7c00);	
			}   			

			// assemble the half from s, e and m.
			return (short)(s | (e << 10) | (m >> 13));
		}
	}

	/**
	 * Converts the raw bits of a 16-bit half float to the raw bits 
	 * of a 32-bit float.
	 * 
	 * @param y the raw bits of a 16-bit half float
	 * @return the raw bits of a 32-bit float
	 * 
	 * */
	public static int toFloatRawBits(int i) {
		int s = (i >> 15) & 0x1;
		int e = (i >> 10) & 0x1f;
		int m =  i & 0x3ff;

		if(e == 0) {
			if(m == 0) {
				// plus or minus zero
				return s << 31;
			} else {
				// denormalized number -- renormalize it
				while((m & 0x400) == 0) {
					m <<= 1;
					e -=  1;
				}
				e += 1; // restore e offset with 1
				m &= ~0x400; // get rid of the implicit leading 1
			}
		} else if(e == 31) {
			if(m == 0) {
				// positive or negative infinity
				return (s << 31) | 0x7f800000;
			} else {
				// NaN -- preserve sign and significand bits
				return (s << 31) | 0x7f800000 | (m << 13);
			}
		}

		// normalized number
		e = e + BIAS_DELTA;
		m = m << 13;

		// Assemble s, e and m.
		return (s << 31) | (e << 23) | m;
	}
}
