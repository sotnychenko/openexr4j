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
 * Preview.java
 * @since 2009-05-03
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_BYTE;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_INTEGER;

import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * Preview.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-03
 * @version $Id$
 *
 */
public class Preview implements AttributeReader {

	/**
	 * Gets width of preview given a byte array.
	 * 
	 * @param byteArray the byte array
	 * @return the width
	 * 
	 * */
	public int getWidth(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, 0);
	}

	/**
	 * Gets height of preview given a byte array.
	 * 
	 * @param byteArray the byte array
	 * @return the height
	 * 
	 * */
	public int getHeight(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, BYTE_NUMBER_OF_INTEGER);
	}

	/**
	 * Gets pixel data given a byte array, width, height, and specified component.
	 * 
	 * @param byteArray the byte array
	 * @param width width (the column)
	 * @param height height (the row)
	 * @param Component component
	 * @return pixel data
	 * 
	 * @see {@link com.aqy.openexr4j.header.attr.Preview.Component}
	 * 
	 * */
	public byte getPixelData(byte[] byteArray, int width, int height, Component c) {
		int index = 2 * BYTE_NUMBER_OF_INTEGER + BYTE_NUMBER_OF_BYTE * (width * height + c.ordinal());
		return ReaderUtils.byteArrayReader.readByte(byteArray, index);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length >= 2 * BYTE_NUMBER_OF_INTEGER;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(byte[] byteArray) {
		int width = getWidth(byteArray);
		int height = getHeight(byteArray);
		return byteArray.length == 2 * 2 * BYTE_NUMBER_OF_INTEGER + BYTE_NUMBER_OF_BYTE * width * height * 4;
	}

	/**
	 * Components of a pixel data: R, G, B, A.
	 * 
	 * */
	public enum Component {
		R,
		G,
		B,
		A;
	}
}
