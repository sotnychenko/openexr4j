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
 * OpenEXRHeader.java
 * @since 2009-04-18
 * 
 * $Id$
 */
package com.aqy.openexr4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.attr.Attribute;
import com.aqy.openexr4j.header.attr.AttributeBuilder;
import com.aqy.openexr4j.header.attr.AttributeType;
import com.aqy.openexr4j.header.attr.IllegalAttributeTypeException;
import com.aqy.openexr4j.header.attr.RequiredAttribute;
import com.aqy.openexr4j.header.io.EndOfInputException;
import com.aqy.openexr4j.header.io.ReaderUtils;
import com.aqy.openexr4j.header.io.ReaderUtils.ReadTillNull;

/**
 * V2Reader.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-18
 * @version $Id$
 *
 */
public class V2Reader {

	// log4j logger
	private final static Logger log = Logger.getLogger(V2Reader.class);

	public final static int ATTRIBUTE_NAME_MIN_LENGTH = 1;
	public final static int ATTRIBUTE_NAME_MAX_LENGTH = 31;

	// collection of required attributes, uses map to check whether 
	// all required ones are set or not
	private final Map<RequiredAttribute, Attribute> requiredAttributes;
	// collection of customized attributes
	private List<Attribute> customizedAttributes;
	
	public V2Reader() {
		// initialize required attribute map
		requiredAttributes = new EnumMap<RequiredAttribute, Attribute>(RequiredAttribute.class);
		for(RequiredAttribute ra : RequiredAttribute.values()) {
			requiredAttributes.put(ra, null);
		}
	}

	public void construct(InputStream stream) throws MalformedHeaderException {
		// read attributes
		readAttributes(stream);

		// check if required attributes are missing
		for(Map.Entry<RequiredAttribute, Attribute> entry : requiredAttributes.entrySet()) {
			if(entry.getValue() == null) {
				throw new MalformedHeaderException(String.format("Missing required attribute. %s", entry.getKey()));
			}
		}
	}

	// reads attributes
	private void readAttributes(final InputStream stream) throws MalformedHeaderException {
		// defines a helper instance to perform a repetitive read
		ReadTillNull readTillNull = new ReadTillNull(stream) {
			@Override
			public void onRead(String attributeName) 
			throws MalformedHeaderException, IOException, EndOfInputException, IllegalAttributeTypeException {
				// read type name
				String typeName = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
				try {
					// find the attribute type, might throw IllegalArgumentException here 
					// if the read attribute type is undefined
					AttributeType type = AttributeType.safeValueOf(typeName);
					int length = ReaderUtils.streamReader.readInteger(stream);
					try {
						// determine if the attribute is a required one
						RequiredAttribute ra = RequiredAttribute.valueOf(attributeName);
						// check if the specified attribute has been set previously
						Attribute set = requiredAttributes.get(type);
						if(set != null) {
							log.warn(String.format("Found attribute type [%s] set previously. Overwrite.", set));
						}
						// put required attributes in a map for later checks
						requiredAttributes.put(ra, ra.build(type, length, stream));
					} catch(IllegalArgumentException iae) {
						// customized attribute
						Attribute customized = AttributeBuilder.build(attributeName, type, length, stream);
						// lazily initialize the list
						if(customizedAttributes == null) {
							customizedAttributes = new ArrayList<Attribute>();
						}
						customizedAttributes.add(customized);
					}
				} catch(IllegalArgumentException iae) {
					// undefined attribute type
					throw new MalformedHeaderException("Attribute type undefined.", iae);
				}
			}
		};
		// start to read attributes
		readTillNull.startRead();
	}

	public List<Attribute> getCustomizedAttributes() {
		return customizedAttributes;
	}
}
