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
 * SequentialReader.java
 * @since 2009-04-29
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.io;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BIT_NUMBER_OF_BYTE;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_INTEGER;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_LONG;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * SequentialReader.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-29
 * @version $Id$
 *
 */
public class StreamReader {
	
	// log4j logger
	private final static Logger log = Logger.getLogger(StreamReader.class);
	
	StreamReader() {
		// default constructor
	}
	
	/**
	 * Reads an integer from an input stream.
	 * 
	 * @param stream the input stream
	 * @return an integer
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public int readInteger(InputStream stream) 
	throws IOException, EndOfInputException {
		int result = 0;
		for(int i=0; i<BYTE_NUMBER_OF_INTEGER; ++i) {
			int read = stream.read();
			checkIfEndOfInput(read);
			// compute the bits to shift to the left
			int bitsToShift =  (i * BIT_NUMBER_OF_BYTE);
			// aggregate the result
			result |= read << bitsToShift;
			
			log.trace(String.format("%d. [0x%x] read, %d bits to shift, resulting [0x%x].", 
					i, read, bitsToShift, result));
		}
		log.debug(String.format("%d bits [0x%x] read.", 
				BYTE_NUMBER_OF_INTEGER * BIT_NUMBER_OF_BYTE, result));
		return result;
	}
	
	/**
	 * Reads a float number from an input stream.
	 * 
	 * @param stream the input stream
	 * @return a float number
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public float readFloat(InputStream stream) 
	throws IOException, EndOfInputException {
		// read int bits of the float number
		int bits = readInteger(stream);
		// get the float by the int bits
		return Float.intBitsToFloat(bits);
	}
	
	/**
	 * Reads a long number from an input stream.
	 * 
	 * @param stream the input stream
	 * @return a long number
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public long readLong(InputStream stream) 
	throws IOException, EndOfInputException {
		long result = 0;
		for(int i=0; i<BYTE_NUMBER_OF_LONG; ++i) {
			int read = stream.read();
			checkIfEndOfInput(read);
			// compute the bits to shift to the left
			int bitsToShift =  (i * BIT_NUMBER_OF_BYTE);
			// aggregate the result
			result |= read << bitsToShift;
			
			log.trace(String.format("%d. [0x%x] read, %d bits to shift, resulting [0x%x].", 
					i, read, bitsToShift, result));
		}
		log.debug(String.format("%d bits [0x%x] read.", 
				BYTE_NUMBER_OF_LONG * BIT_NUMBER_OF_BYTE, result));
		return result;
	}
	
	/**
	 * Reads a double number from an input stream.
	 * 
	 * @param stream the input stream
	 * @return a double number
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public double readDouble(InputStream stream) 
	throws IOException, EndOfInputException {
		// read the long bits of the double
		long bits = readLong(stream);
		// get the double by the long bits
		return Double.longBitsToDouble(bits);
	}
	
	/**
	 * Reads a byte from an input stream.
	 * 
	 * @param stream the input stream
	 * @return a byte
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public byte readByte(InputStream stream) 
	throws IOException, EndOfInputException {
		Integer read = stream.read();
		checkIfEndOfInput(read);
		return read.byteValue();
	}
	
	/**
	 * Reads a char from an input stream.
	 * 
	 * @param stream the input stream
	 * @return a char
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public char readChar(InputStream stream) 
	throws IOException, EndOfInputException {
		return (char)readByte(stream);
	}
	
	/**
	 * Reads a char array from an input stream, provided length of the result array.
	 * 
	 * @param stream the input stream
	 * @param length the length of the result array
	 * @return the char array
	 * 
	 * @throws IOException IOException caused by stream
	 * @throws EndOfInputException unexpected end of input
	 * 
	 * */
	public char[] readCharArray(InputStream stream, int length) 
	throws IOException, EndOfInputException {
		char[] array = new char[length];
		for(int i=0; i<length; ++i) {
			Integer read = stream.read();
			checkIfEndOfInput(read);
			array[i] = (char)read.byteValue();
		}
		return array;
	}
	
	/**
	 * Reads a null-terminated string from an input stream provided length 
	 * requirements for maximum length.
	 * 
	 * @param stream the input stream
	 * @param maximum the maximum length
	 * @return a string
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input, stream too short
	 * @throws StringTooLongException string exceeds the maximum length
	 * 
	 * */
	public String readString(InputStream stream, int maximum)
	throws IOException, EndOfInputException, StringTooLongException {
		try {
			// provides a minimum length of -1 to ignore the minimum length requirement
			return readString(stream, -1, maximum);
		} catch(StringTooShortException stse) {
			// string length is impossible to be under -1, should never 
			// get here!
			assert(false);
			log.error("This should never happen.", stse);
			return "";
		}
	}

	/**
	 * Reads a null-terminated string from an input stream provided length 
	 * requirements for minimum length and maximum length.
	 * 
	 * @param stream the input stream
	 * @param minimum the minimum length
	 * @param maximum the maximum length
	 * @return a string
	 * 
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException unexpected end of input, stream too short
	 * @throws StringTooLongException string exceeds the maximum length
	 * @throws StringTooShortException string length is under minimum length requirement
	 * 
	 * */
	public String readString(InputStream stream, int minimum, int maximum) 
	throws IOException, EndOfInputException, StringTooLongException, StringTooShortException {
		StringBuilder builder = new StringBuilder();
		boolean nullRead = false;
		for(int i=0; i<=maximum; ++i) {
			int read = stream.read();
			// check if reaches the end of input unexpectedly
			checkIfEndOfInput(read);
			if(read == 0) {
				// end of string
				nullRead = true;
				break;
			} else {
				builder.append((char)read);
			}
		}
		if(!nullRead) {
			// string too long
			throw new StringTooLongException(builder.toString(), maximum);
		} else if(builder.length() < minimum) {
			// string too short
			throw new StringTooShortException(builder.toString(), minimum);
		}
		return builder.toString();
	}
	
	// check if end of input read unexpectedly
	private void checkIfEndOfInput(int read) throws EndOfInputException {
		if(read == -1) {
			throw new EndOfInputException(
					"Unexpectedly reached end of input stream while reading null-terminated string.");
		}		
	}
}
