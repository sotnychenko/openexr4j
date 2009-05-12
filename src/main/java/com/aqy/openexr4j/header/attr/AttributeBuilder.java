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
 * AttributeBuilder.java
 * @since 2009-05-03
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.IOException;
import java.io.InputStream;

import com.aqy.openexr4j.header.MalformedHeaderException;

/**
 * AttributeBuilder.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-03
 * @version $Id$
 *
 */
public class AttributeBuilder {

	/**
	 * 
	 * */
	private AttributeBuilder() {
		// private constructor
	}
	
	/**
	 * Builds an attribute provided its name, type, length, and raw data.
	 * 
	 * @param name attribute name
	 * @param type attribute type
	 * @param length length of input
	 * @param reader reader of input stream
	 * @return the attribute
	 * 
	 * */
	public static Attribute build(final String name, final AttributeType type, final int length, InputStream stream) 
	throws IllegalAttributeTypeException, MalformedHeaderException, IOException {
		// attribute reader will validate the raw data
		AttributeReader attributeReader = type.getReader();
		byte[] byteArray = null;
		if(attributeReader.accept(length)) {
			// create a byte array buffer
			byteArray = new byte[length];
			// read bytes into the buffer
			int numOfRead = stream.read(byteArray);
			// check if the number of bytes read is the same as expected
			if(length != numOfRead) {
				// number of bytes read less than expected
				throwMalformedHeaderException(length, numOfRead);
			}
			// check if the attribute reader can understand the byte array
			if(!attributeReader.accept(byteArray)) {
				throwMalformedHeaderException(attributeReader, byteArray);
			}
		} else {
			// the reader expects a different lenght of input
			throwMalformedHeaderException(attributeReader, length);
		}
		assert(byteArray != null);
		return newInstance(name, type, byteArray, length);
	}
	
	// instantiate the actual attribute
	private static Attribute newInstance(final String name, final AttributeType type, final byte[] byteArray, final int length) {
		Attribute a =  new Attribute() {
			@Override
			public String getName() {
				return name;
			}
			@Override
			public byte[] getRawData() {
				return byteArray;
			}
			@Override
			public int getSize() {
				return length;
			}
			@Override
			public AttributeType getType() {
				return type;
			}
		};
		return a;
	}
	
	// throws MalformedHeaderException due to the number of bytes actually read is 
	// not the same as expected
	private static void throwMalformedHeaderException(int expectedLength, int actualRead) 
	throws MalformedHeaderException {
		throw new MalformedHeaderException(String.format(
				"Reached unexpected end of input. Expected: %d, actual: %d.", expectedLength, actualRead));
	}
	
	// throws MalformedHeaderException due to the attribute reader is not able to understand  
	// the byte array
	private static void throwMalformedHeaderException(AttributeReader reader, byte[] byteArray) 
	throws MalformedHeaderException {
		throw new MalformedHeaderException(String.format(
				"%s does not understand %s", reader, byteArray));
	}
	
	// throws MalformedHeaderException due to the reader expects a different length 
	// of input
	private static void throwMalformedHeaderException(AttributeReader reader, int length) 
	throws MalformedHeaderException {
		throw new MalformedHeaderException(String.format(
				"%s expected a different length than %d.", reader, length));
	}
}
