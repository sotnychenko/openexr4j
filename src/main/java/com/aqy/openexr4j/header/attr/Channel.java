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
 * ImageChannelReader.java
 * @since 2009-04-19
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_CHARACTER;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_INTEGER;

import java.util.Map;

import com.aqy.openexr4j.header.DataType;
import com.aqy.openexr4j.header.attr.AttributeUtils.AttributeComponent;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * Channel.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-19
 * @version $Id$
 *
 */
public class Channel implements AttributeReader {
	
	// three bits for reserved
	private static final int NUMBER_OF_RESERVED_BYTE = 3;
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length == expectedLength();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(byte[] byteArray) {
		return true;
	}
	
	/**
	 * the expected length
	 * 
	 * @return the expected length
	 * 
	 * */
	public int expectedLength() {
		return AttributeUtils.totalLength(Component.values());
	}

	/**
	 * Sets up the channel with provided byte array.
	 * 
	 * @param channel the channel to setup
	 * @param byteArray the raw byte array
	 * 
	 * */
	public void setup(ImageChannel channel, byte[] byteArray) {
		// type
		channel.setType(getPixelType(byteArray));
		// plinear
		channel.setPLinear(getPLinear(byteArray));
		// reserved
		channel.setReserved(getReserved(byteArray));
		// xsample
		channel.setXSampling(getXSampling(byteArray));
		// ysample
		channel.setYSampling(getYSampling(byteArray));
	}

	/**
	 * Gets the pixel type.
	 * 
	 * @param byteArray the byte array
	 * @return data type, UNIT, FLOAT, or HALF
	 * 
	 * */
	public DataType getPixelType(byte[] byteArray) {
		int index = ReaderUtils.byteArrayReader.readInteger(byteArray, Component.DataType.offset());
		return DataType.values()[index];
	}

	/**
	 * Gets the plinear byte.
	 * 
	 * @param byteArray the byte array
	 * @return the plinear byte
	 * 
	 * */
	public byte getPLinear(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readByte(byteArray, Component.PLinear.offset());
	}

	/**
	 * Gets the researved bits.
	 * 
	 * @param byteArray the byte array
	 * @return the 3-bit reserved bits
	 * 
	 * */
	public char[] getReserved(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readCharArray(byteArray, Component.Reserved.offset(), NUMBER_OF_RESERVED_BYTE);
	}

	/**
	 * Gets x sampling.
	 * 
	 * @param byteArray the byte array
	 * @return sampling rate on x direction
	 * 
	 * */
	public int getXSampling(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, Component.XSampling.offset());
	}

	/**
	 * Gets y sampling.
	 * 
	 * @param byteArray the byte array
	 * @return sampling rate on y direction
	 * 
	 * */
	public int getYSampling(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, Component.YSampling.offset());
	}
	
	/**
	 * Channel Layout:<br>
	 * <br>
	 * DataType - 1 byte, possible values:<br>
	 * UINT, HALF, FLOAT<br>
	 * PLinear - 1 byte<br>
	 * Reserved - 3 bits<br>
	 * XSampling - integer<br>
	 * YSampling - integer
	 * 
	 * */
	public enum Component implements AttributeComponent {
		DataType,
		PLinear {
			@Override
			public int length() {
				return BYTE_NUMBER_OF_CHARACTER;
			}
		},
		Reserved {
			@Override
			public int length() {
				return BYTE_NUMBER_OF_CHARACTER * NUMBER_OF_RESERVED_BYTE;
			}
		},
		XSampling,
		YSampling;
		
		static final Map<Component, Integer> offsetLut = AttributeUtils.setupLut(Component.class, Component.values());

		@Override
		public int offset() {
			return offsetLut.get(this);
		}
		@Override
		public int length() {
			return BYTE_NUMBER_OF_INTEGER;
		}
	}
}
