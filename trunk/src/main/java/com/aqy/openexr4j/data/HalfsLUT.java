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
 * HalfsLUT.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-11
 * @version $Id$
 *
 */
class HalfsLUT {

	private final static Logger log = Logger.getLogger(HalfsLUT.class);
	private final static HalfsLUT instance = new HalfsLUT();
	
	private final static int SELUT_SIZE = 1 << 9;
	private static int[] SELUT;
	
	private final static int FLUT_SIZE = 1 << 16;
	private static float[] FLUT;
	
	static HalfsLUT getInstance() {
		return instance;
	}
	
	private HalfsLUT() {
		// initialize selut table
		SELUT = new int[SELUT_SIZE];
		for(int i = 0, n = SELUT_SIZE >> 1; i < n; i++) {
			int e = (i & 0x0ff) - HalfUtils.BIAS_DELTA;
			if(e <= 0 || e >= 30) {
				// special case, need to call HalfUtils.toFP16BitRawBits()
				// 0000,00[00,0000,0000]
				SELUT[i] = 0;
				SELUT[i | 0x100] = 0;
			} else {
				// common case - normalized half, no exponent overflow possible
				// [0|1]###,##[00,0000,0000]
				SELUT[i] = (short)(e << 10);
				SELUT[i | 0x100] = (short)((e << 10) | 0x8000);
			}
		}
		// initialize flut table
		FLUT = new float[FLUT_SIZE];
		for(int i = 0; i < FLUT_SIZE; ++i) {
			FLUT[i] = Float.intBitsToFloat(HalfUtils.toFloatRawBits(i));
		}
	}
	
	/**
	 * Looks up the 16-bit [sign | exponent] given a 32-bit counterpart
	 * 
	 * @param a32BitSE 32-bit [sign | exponent]
	 * @return the 16-bit [sign | exponent]
	 * 
	 * */
	public int get16BitSE(int a32BitSE) {
		assert(a32BitSE < SELUT_SIZE);
		int the16BitSE = SELUT[a32BitSE];
		log.trace(String.format("Hit se [0x%x(32Bit)] for [0x%x(16Bit<<10)].", a32BitSE, the16BitSE));
		return the16BitSE;
	}
	
	/**
	 * Looks up the 32-bit float given a 16-bit half float in raw bits form
	 * 
	 * @param a16BitRawBits the raw bits form of a 16-bit half float
	 * @return the corresponding 32-bit float
	 * 
	 * */
	public float get32BitFloat(short a16BitRawBits) {
		int index = a16BitRawBits & 0xffff;
		float f = get32BitFloat(index);
		log.trace(String.format("Hit float [0x%x(16Bit)] for [0x%x(32Bit)] @ [0x%x(index)].", 
				a16BitRawBits, Float.floatToRawIntBits(f), index));
		return f;
	}
	
	float get32BitFloat(int index) {
		assert(index < FLUT_SIZE);
		return FLUT[index];
	}
	
	/**
	 * Looks up the 32-bit float given a 16-bit half float
	 * 
	 * @param half the 16-bit half float
	 * @return the corresponding 32-bit float
	 * 
	 * */
	public float get32BitFloat(Half half) {
		return get32BitFloat(Half.halfToRawShortBits(half));
	}
}
