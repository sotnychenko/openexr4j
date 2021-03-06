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
 * IllegalAttributeTypeException.java
 * @since 2009-04-18
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;



/**
 * IllegalAttributeTypeException.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-18
 * @version $Id$
 *
 */
public class IllegalAttributeTypeException extends Exception {

	private final RequiredAttribute attribute;
	private final AttributeType attributeType;
	
	/**
	 * 
	 * 
	 * */
	public IllegalAttributeTypeException(RequiredAttribute attribute, AttributeType attributeType) {
		super(String.format("Illegal attribute reader [%s] for [%s]", attributeType, attribute));
		this.attribute = attribute;
		this.attributeType = attributeType;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}

	/**
	 * @return the attribute
	 */
	public RequiredAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the attributeType
	 */
	public AttributeType getAttributeType() {
		return attributeType;
	}

	private static final long serialVersionUID = -8281993327764335175L;
}
