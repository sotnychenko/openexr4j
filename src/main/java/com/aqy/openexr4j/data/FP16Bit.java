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

/**
 * FP16Bit.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-07
 * @version $Id$
 *
 */
public interface FP16Bit {

	public FP16Bit add(Float f);
	public FP16Bit sub(Float f);
	public FP16Bit mul(Float f);
	public FP16Bit div(Float f);
	
	public FP16Bit negate();
	
	public FP16Bit round(int n);
	
	public void addAndSet(Float f);
	public void subAndSet(Float f);
	public void mulAndSet(Float f);
	public void divAndSet(Float f);
	
	public boolean equals(Float f);
	public boolean lessThan(Float f);
	public boolean greaterThan(Float f);
	
	public boolean isFinite();
	public boolean isNormalized();
	public boolean isDenormalized();
	public boolean isZero();
	public boolean isNaN();
	public boolean isInfinity();
	public boolean isNegative();
	
	public float floatValue();
}
