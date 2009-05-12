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
 *
 * Compression.java
 * @since 2009-04-21
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_BYTE;

/**
 * LineOrder.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-21
 * @version $Id$
 *
 */
public class LineOrder implements AttributeReader {
	
	/**
	 * Gets line order value given a byte array.
	 * 
	 * @param byteArray the byte array
	 * @return the line order
	 * 
	 * @see {@link com.aqy.openexr4j.header.attr.LineOrder.LineOrderValue}
	 * 
	 * */
	public LineOrderValue get(byte[] byteArray) {
		byte index = byteArray[0];
		return LineOrderValue.values()[index];
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length == BYTE_NUMBER_OF_BYTE;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(byte[] byteArray) {
		return byteArray[0] < LineOrderValue.values().length;
	}
	
	/**
	 * Line order values.
	 * 
	 * */
	public enum LineOrderValue {
		/**
		 * 
		 * */
		INCREASING_Y,
		
		/**
		 * 
		 * */
		DECREASING_Y,
		
		/**
		 * 
		 * */
		RANDOM_Y;
	}
}
