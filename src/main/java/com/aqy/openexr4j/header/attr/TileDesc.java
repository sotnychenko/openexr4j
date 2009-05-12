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
 * TileDescription.java
 * @since 2009-05-02
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_BYTE;
import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_INTEGER;

import java.util.Map;

import com.aqy.openexr4j.header.LevelMode;
import com.aqy.openexr4j.header.RoundingMode;
import com.aqy.openexr4j.header.attr.AttributeUtils.AttributeComponent;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * TileDesc.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-02
 * @version $Id$
 *
 */
public class TileDesc implements AttributeReader {

	/**
	 * Gets the size along x axis given a byte array.
	 * 
	 * @param byteArray the byte Array
	 * @return the size along x axis
	 * 
	 * */
	public int getXSize(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, Component.XSize.offset());
	}

	/**
	 * Gets the size along y axis given a byte array.
	 * 
	 * @param byteArray the byte Array
	 * @return the size along y axis
	 * 
	 * */
	public int getYSize(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readInteger(byteArray, Component.YSize.offset());
	}

	/**
	 * Gets the level mode of the tiles.
	 * 
	 * @param byteArray the byte array
	 * @return the level mode
	 * 
	 * @see {@link com.aqy.openexr4j.header.LevelMode}
	 * 
	 * */
	public LevelMode getLevelMode(byte[] byteArray) {
		int index = getMode(byteArray) & 0x0f;
		return LevelMode.values()[index];
	}

	/**
	 * Gets the rounding mode of the tiles.
	 * 
	 * @param byteArray the byte array
	 * @return the rounding mode
	 * 
	 * @see {@link com.aqy.openexr4j.header.RoundingMode}
	 * 
	 * */
	public RoundingMode getRoundingMode(byte[] byteArray) {
		int index = getMode(byteArray) >> 4;
		return RoundingMode.values()[index];
	}

	// gets the byte of level mode and rounding mode
	private byte getMode(byte[] byteArray) {
		return ReaderUtils.byteArrayReader.readByte(byteArray, Component.Mode.offset());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length == AttributeUtils.totalLength(Component.values());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(byte[] byteArray) {
		int mode = getMode(byteArray);
		int level = mode & 0x0f;
		int rounding = mode >> 4;
		return level < LevelMode.values().length && rounding < RoundingMode.values().length;
	}

	/**
	 * The component of the tile description.
	 * 
	 * */
	public enum Component implements AttributeComponent {
		XSize,
		YSize,
		Mode {
			@Override
			public int length() {
				return BYTE_NUMBER_OF_BYTE;
			}
		};

		static final Map<Component, Integer> offsetLut = AttributeUtils.setupLut(Component.class, Component.values());

		@Override
		public int length() {
			return BYTE_NUMBER_OF_INTEGER;
		}
		@Override
		public int offset() {
			return offsetLut.get(this);
		}
	}
}
