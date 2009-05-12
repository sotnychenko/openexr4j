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

import java.util.Map;

import com.aqy.openexr4j.header.attr.AttributeUtils.AttributeComponent;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * Envmap.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-21
 * @version $Id$
 *
 */
public class Envmap implements AttributeReader {
	
	/**
	 * Gets the <code>Component</code> provided the raw data.
	 * 
	 * @see {@link com.aqy.openexr4j.header.attr.Emvmap.Component}
	 * 
	 * */
	public byte get(byte[] byteArray, Component c) {
		return ReaderUtils.byteArrayReader.readByte(byteArray, c.offset());
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
		return true;
	}
	
	/**
	 * Components for Envmap.<br>
	 * <br>
	 * Two <i>unsigned char</i>s: ENVMAP_LATLONG, ENVMAP_CUBE
	 * 
	 * */
	public enum Component implements AttributeComponent {
		ENVMAP_LATLONG,
		ENVMAP_CUBE;

		static final Map<Component, Integer> offsetLut = AttributeUtils.setupLut(Component.class, Component.values());
		
		@Override
		public int length() {
			return BYTE_NUMBER_OF_BYTE;
		}
		@Override
		public int offset() {
			return offsetLut.get(this);
		}
	}
}
