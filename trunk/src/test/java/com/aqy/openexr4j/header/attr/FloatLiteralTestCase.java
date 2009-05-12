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
 * Box2fTestCase.java
 * @since 2009-05-05
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * Box2fTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-05
 * @version $Id$
 *
 */
public class FloatLiteralTestCase extends TestCase {

	public static final byte[] data = {
		0x66, 0x6c, 0x6f, 0x61, 0x74, 0x00, // "float\0"
		0x04, 0x0, 0x0, 0x0, // 4
		0x0, 0x0, (byte)0x80, 0x3f, // 1.0
	};
	
	public static final byte[] length_too_long = {
		0x66, 0x6c, 0x6f, 0x61, 0x74, 0x00, // "float\0"
		0x05, 0x0, 0x0, 0x0, // 5
		0x0, 0x0, (byte)0x80, 0x3f, // 1.0
	};
	
	public static final byte[] length_too_short = {
		0x66, 0x6c, 0x6f, 0x61, 0x74, 0x00, // "float\0"
		0x03, 0x0, 0x0, 0x0, // 3
		0x0, 0x0, (byte)0x80, 0x3f, // 1.0
	};
	
	public static final byte[] invalid = {
		0x66, 0x6c, 0x6f, 0x61, 0x74, 0x00, // "float\0"
		0x03, 0x0, 0x0, 0x0, // 3
		0x0, 0x0, (byte)0x80, // missing one byte
	};


	public void testNormal() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("float", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.float_literal, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(4, length);

		Attribute attribute = AttributeBuilder.build("custom", at, length, stream);
		assertEquals("custom", attribute.getName());
		byte[] byteArray = attribute.getRawData();
		float f = AttributeReader.FloatLiteral.get(byteArray);
		assertEquals(1.f, f);
	}

	public void testLengthTooLong() throws Exception {
		InputStream stream = new ByteArrayInputStream(length_too_long);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("float", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.float_literal, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(5, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	public void testLengthTooShort() throws Exception {
		InputStream stream = new ByteArrayInputStream(length_too_short);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("float", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.float_literal, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(3, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	public void testInvalid() throws Exception {
		InputStream stream = new ByteArrayInputStream(invalid);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("float", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.float_literal, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(3, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
}
