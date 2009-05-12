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
 * RequiredAttribute.java
 * @since 2009-04-18
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.IOException;
import java.io.InputStream;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.io.EndOfInputException;


/**
 * RequiredAttribute.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-18
 * @version $Id$
 *
 */
public enum RequiredAttribute {	
	displayWindow {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.box2i;
		}
	},
	dataWindow {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.box2i;
		}
	},
	pixelAspectRatio {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.float_literal;
		}
	},
	channels {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.chlist;
		}
	},
	compression {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.compression;
		}
	},
	lineOrder {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.lineOrder;
		}
	},
	screenWindowWidth {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.float_literal;
		}
	},
	screenWindowCenter {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.v2f;
		}
	},
	tiles {
		@Override
		protected boolean accept(AttributeType type) {
			return type == AttributeType.tiledesc;
		}
	};

	/**
	 * Builds the attribute provided attribute type and input length
	 * 
	 * @param type the attribute type
	 * @param length input length
	 * @param stream input stream
	 * @return the attribute
	 * 
	 * @throws IllegalAttributeTypeException the attribute doesn't accept the attribute type
	 * @throws MalformedHeaderException the type reader doesn't understand the input
	 * @throws IOException IOException caused by input stream
	 * @throws EndOfInputException the input stream reaches the end unexpectedly, this could 
	 * happen when process a varying-length input(i.e., chlist) and the input was missing some 
	 * data
	 * 
	 * */
	public Attribute build(AttributeType type, int length, InputStream stream) 
	throws IllegalAttributeTypeException, MalformedHeaderException, IOException, EndOfInputException {
		if(!accept(type)) {
			throw new IllegalAttributeTypeException(this, type);
		}
		return AttributeBuilder.build(toString(), type, length, stream);
	}
	
	/**
	 * Determines if the predefined attribute accepts the attribute type.
	 * 
	 * @param type the attribute type
	 * @return whether the predefined attribute accepts the attribute type
	 * 
	 * */
	protected abstract boolean accept(AttributeType type);
}
