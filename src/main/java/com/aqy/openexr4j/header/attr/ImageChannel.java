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
 * ImageChannel.java
 * @since 2009-04-18
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.IOException;
import java.io.InputStream;

import com.aqy.openexr4j.header.DataType;
import com.aqy.openexr4j.header.MalformedHeaderException;

/**
 * ImageChannel.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-18
 * @version $Id$
 *
 */
public abstract class ImageChannel {

	// channel layout is fixed, therefore, a determined reader will be used
	private static final Channel reader = new Channel();

	// channel type has a fixed expected length
	private static final int expectedLength = reader.expectedLength();

	// private member
	private DataType type;
	private byte pLinear;
	private char[] reserved;
	private int xSampling;
	private int ySampling;

	/**
	 * Builds the channel provided name and input stream.
	 * 
	 * @param name channel name
	 * @param stream input stream
	 * @return the constructed channel
	 * 
	 * @throws MalformedException channel layout is not readable
	 * @throws IOException IO problem when reading input stream
	 * 
	 * */
	public static ImageChannel build(final String name, InputStream stream) 
	throws MalformedHeaderException, IOException {
		// create a byte array buffer
		byte[] byteArray = new byte[expectedLength];
		// read bytes into the buffer
		int numOfRead = stream.read(byteArray);
		// check if the number of bytes read is the same as expected
		if(expectedLength != numOfRead) {
			// number of bytes read less than expected
			throwMalformedHeaderException(expectedLength, numOfRead);
		}
		return new ImageChannel(reader, byteArray) {
			@Override
			public String getName() {
				return name;
			}
		};
	}

	// throws MalformedHeaderException due to the number of bytes actually read is 
	// not the same as expected
	private static void throwMalformedHeaderException(int expectedLength, int actualRead) 
	throws MalformedHeaderException {
		throw new MalformedHeaderException(String.format(
				"Reached unexpected end of input. Expected: %d, actual: %d.", expectedLength, actualRead));
	}

	// private constructor
	private ImageChannel(Channel reader, byte[] byteArray) {
		// reader reads the byte data and sets up the channel
		reader.setup(this, byteArray);
	}

	/**
	 * Each channel has a name, which could be anything 
	 * as long as meets the string length requirement.
	 * 
	 * @return channel name
	 * 
	 * */
	public abstract String getName();

	/**
	 * @return the type
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DataType type) {
		this.type = type;
	}

	/**
	 * @return the pLinear
	 */
	public byte getPLinear() {
		return pLinear;
	}

	/**
	 * @param linear the pLinear to set
	 */
	public void setPLinear(byte linear) {
		pLinear = linear;
	}

	/**
	 * @return the reserved
	 */
	public char[] getReserved() {
		return reserved;
	}

	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(char[] reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return the xSampling
	 */
	public int getXSampling() {
		return xSampling;
	}

	/**
	 * @param sampling the xSampling to set
	 */
	public void setXSampling(int sampling) {
		xSampling = sampling;
	}

	/**
	 * @return the ySampling
	 */
	public int getYSampling() {
		return ySampling;
	}

	/**
	 * @param sampling the ySampling to set
	 */
	public void setYSampling(int sampling) {
		ySampling = sampling;
	}
}
