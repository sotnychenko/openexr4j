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
 * AttributeIOBase.java
 * @since 2009-04-26
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.util.EnumMap;
import java.util.Map;

/**
 * AttributeIOBase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-26
 * @version $Id$
 *
 */
class AttributeUtils {
	
	/**
	 * 
	 * */
	private AttributeUtils() {
		// private constructor
	}
	
	/**
	 * interface to help calculating offset and total length of attribute components.
	 * 
	 * */
	interface AttributeComponent {
		/**
		 * @return the offset of each component
		 * 
		 * */
		int offset();
		/**
		 * @return the length of each component
		 * 
		 * */
		int length();
	}
	
	/**
	 * Initializes the offset look-up table.
	 * 
	 * @param c the component class, help to setup EnumMap
	 * @param values the value of components
	 * @return the offset look-up table
	 * 
	 * */
	static <E extends Enum<E> & AttributeComponent> Map<E, Integer> setupLut(Class<E> c, E[] values) {
		Map<E, Integer> lut = new EnumMap<E, Integer>(c);
		int offset = 0;
		for(E key : values) {
			lut.put(key, offset);
			offset += key.length();
		}
		return lut;
	}
	
	/**
	 * Computes the total length of the attribute components.
	 * 
	 * @param values the value of components
	 * @return the total length of the components
	 * 
	 * */
	static <E extends Enum<E> & AttributeComponent> int totalLength(E[] values) {
		E last = values[values.length - 1];
		return last.offset() + last.length();
	}
}
