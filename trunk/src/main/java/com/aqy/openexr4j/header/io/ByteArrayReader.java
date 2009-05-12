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
 * RandomReader.java
 * @since 2009-04-29
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.io;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BIT_NUMBER_OF_BYTE;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_INTEGER;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_LONG;

import org.apache.log4j.Logger;

/**
 * RandomReader.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-29
 * @version $Id$
 *
 */
public class ByteArrayReader {
	
	// log4j logger
	private final static Logger log = Logger.getLogger(ByteArrayReader.class);
	
	/**
	 * ReaderUtils.byteArrayReader should be used as public access.
	 * 
	 * @see {@link com.aqy.openexr4j.header.io.ReaderUtils}
	 * 
	 */
	ByteArrayReader() {
		// default constructor
	}

	/**
	 * Reads an integer at the offset of a byte array.
	 * 
	 * @param byteAttay the byte array
	 * @param offset the offset
	 * @return an integer
	 * 
	 * */
	public int readInteger(byte[] byteArray, int offset) {
		int result = 0;
		// read integer byte by byte
		for(int i=0; i<BYTE_NUMBER_OF_INTEGER; ++i) {
			// uses short since the data is an unsigned byte
			short s = (short)(byteArray[offset + i] & 0xff);
			// compute the bits to shift to the left
			int bitsToShift =  (i * BIT_NUMBER_OF_BYTE);
			// aggregate the result
			result |= s << bitsToShift;
			
			log.trace(String.format("%d. [0x%x] read, %d bits to shift, resulting [0x%x].", 
					i, s, bitsToShift, result));
		}
		log.debug(String.format("%d bits [0x%x] read.", 
				BYTE_NUMBER_OF_INTEGER * BIT_NUMBER_OF_BYTE, result));
		return result;
	}
	
	/**
	 * Reads a float at the offset of a byte array.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @return a float number
	 * 
	 * */
	public float readFloat(byte[] byteArray, int offset) {
		// reads the raw bits of the float
		int bits = readInteger(byteArray, offset);
		// gets the float by raw bits
		return Float.intBitsToFloat(bits);
	}

	/**
	 * Reads a long at the offset of a byte array.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @return a long number
	 * 
	 * */
	public long readLong(byte[] byteArray, int offset) {
		long result = 0;
		// read integer byte by byte
		for(int i=0; i<BYTE_NUMBER_OF_LONG; ++i) {
			// uses short since the data is an unsigned byte
			short s = (short)(byteArray[offset + i] & 0xff);
			// compute the bits to shift to the left
			int bitsToShift =  (i * BIT_NUMBER_OF_BYTE);
			// aggregate the result
			result |= s << bitsToShift;
			
			log.trace(String.format("%d. [0x%x] read, %d bits to shift, resulting [0x%x].", 
					i, s, bitsToShift, result));
		}
		log.debug(String.format("%d bits [0x%x] read.", 
				BYTE_NUMBER_OF_LONG * BIT_NUMBER_OF_BYTE, result));
		return result;
	}
	
	/**
	 * Reads a double number at the offset of a byte array.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @return a double number
	 * 
	 * */
	public double readDouble(byte[] byteArray, int offset) {
		// read raw bits of a double number
		long bits = readLong(byteArray, offset);
		// get the double number by the raw bits
		return Double.longBitsToDouble(bits);
	}

	/**
	 * Reads a byte at the offset of a byte array.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @return a byte
	 * 
	 * */
	public byte readByte(byte[] byteArray, int offset) {
		return byteArray[offset];
	}
	
	/**
	 * Reads a char at the offset of a byte array.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @return a char
	 * 
	 * */
	public char readChar(byte[] byteArray, int offset) {
		return (char)byteArray[offset];
	}
	
	/**
	 * Reads a char array of provided length at the offset of a byte array.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @param length the length of the result char array
	 * @return the char array
	 * 
	 * */
	public char[] readCharArray(byte[] byteArray, int offset, int length) {
		char[] array = new char[length];
		for(int i=0; i<length; ++i) {
			array[i] = (char)byteArray[offset + i];
		}
		return array;
	}
	
	/**
	 * Reads a null-terminated string at the offset of a byte array. It also provides 
	 * a length requirement for minimum length and maximum length.
	 * 
	 * @param byteArray the byte array
	 * @param offset the offset
	 * @param minimum the minimum length
	 * @param maximum the maximum length
	 * @return a string meets the length requirement
	 * 
	 * @throws EndOfInputException unexpected end of input
	 * @throws StringTooLongException string exceeds the maximum length
	 * @throws StringTooShortException string length is under minimum length requirement
	 * 
	 * */
	public String readString(byte[] byteArray, int offset, int minimum, int maximum) 
	throws EndOfInputException, StringTooLongException, StringTooShortException {
		StringBuilder builder = new StringBuilder();
		boolean nullRead = false;
		// maximum reads could be maximum + 1, the one is the terminator, '\0'
		for(int i=0; i <= maximum; ++i) {
			if(i < byteArray.length) {
				byte b = byteArray[offset + i];
				if(b == 0) {
					// the end of string
					nullRead = true;
					break;
				} else {
					builder.append((char)b);
				}
			} else {
				// array too short
				throwEndOfInputeException(builder);
			}
		}
		if(!nullRead) {
			// too long
			throw new StringTooLongException(builder.toString(), maximum);
		} else if(builder.length() < minimum) {
			// too short
			throw new StringTooShortException(builder.toString(), minimum);
		}
		return builder.toString();
	}
	
	// no null-ternimator of string read but reaches the end of input
	private void throwEndOfInputeException(StringBuilder builder) throws EndOfInputException {
		throw new EndOfInputException(String.format(
				"Reaches the end of input without null-terminator. [%s]", builder));
	}
}
