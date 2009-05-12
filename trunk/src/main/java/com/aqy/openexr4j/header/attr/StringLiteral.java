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
 * StringLiterature.java
 * @since 2009-05-03
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_CHARACTER;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_INTEGER;

import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * StringLiterature.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-03
 * @version $Id$
 *
 */
public class StringLiteral implements AttributeReader {

	/**
	 * Gets the length of the string given a byte array (raw data).
	 * 
	 * @param byteArray the byte array
	 * @return the length
	 * 
	 * */
	public int getLength(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, 0);
	}

	/**
	 * Gets the char array of the string given a byte array (raw data).
	 * 
	 * @param byteArray the byte array
	 * @return the char array
	 * 
	 * */
	public char[] getChars(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readCharArray(byteArray, BYTE_NUMBER_OF_INTEGER, getLength(byteArray));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length >= BYTE_NUMBER_OF_INTEGER;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(byte[] byteArray) {
		int length = getLength(byteArray);
		return byteArray.length == BYTE_NUMBER_OF_INTEGER + length * BYTE_NUMBER_OF_CHARACTER;
	}
}
