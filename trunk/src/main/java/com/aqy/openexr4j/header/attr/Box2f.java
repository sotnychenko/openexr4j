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
 * Box2f.java
 * @since 2009-04-19
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import static com.aqy.openexr4j.header.io.BasicDataTypeConstant.BYTE_NUMBER_OF_FLOAT;

import java.util.Map;

import com.aqy.openexr4j.header.attr.AttributeUtils.AttributeComponent;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * Box2f.java<br>
 * <br>
 * Four <i>float</i>s: xMin, yMin, xMax, yMax
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-19
 * @version $Id$
 *
 */
public class Box2f implements AttributeReader {

	/**
	 * Gets the <code>Component</code> provided the raw data.
	 * 
	 * @see {@link com.aqy.openexr4j.header.attr.Box2f.Component}
	 * 
	 * */
	public float get(byte[] byteArray, Component c) {
		return ReaderUtils.byteArrayReader.readFloat(byteArray, c.offset());
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
	 * Components for Box2f.<br>
	 * <br>
	 * Four <i>float</i>s: xMin, yMin, xMax, yMax
	 * 
	 * */
	public enum Component implements AttributeComponent {
		XMin,
		YMin,
		XMax,
		YMax;
		
		static final Map<Component, Integer> offsetLut = AttributeUtils.setupLut(Component.class, Component.values());
		
		@Override
		public int offset() {
			return offsetLut.get(this);
		}
		@Override
		public int length() {
			return BYTE_NUMBER_OF_FLOAT;
		}
	}
}
