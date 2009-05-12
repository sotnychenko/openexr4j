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
import com.aqy.openexr4j.header.attr.Box2f.Component;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * Box2fTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-05-05
 * @version $Id$
 *
 */
public class Box2fTestCase extends TestCase {

	public static final byte[] data = {
		0x62, 0x6f, 0x78, 0x32, 0x66, 0x0, // "box2f\0"
		0x10, 0x0, 0x0, 0x0, // 16
		0x0, 0x0, 0x0, 0x0, // 0
		0x0, 0x0, (byte)0x20, 0x41, // 10.0, 0 1000001|0 0100000|00000000|00000000
		0x0, 0x0, (byte)0x20, (byte)0xc1, // -10.0, 1 1000001|0 0100000|00000000|00000000
		0x0, 0x0, (byte)0x80, 0x3f, // 1.0, 0 0111111|1 0000000|00000000|00000000
	};

	public static final byte[] malformed_invalid_length = {
		0x62, 0x6f, 0x78, 0x32, 0x66, 0x0, // "box2f\0"
		0x0f, 0x0, 0x0, 0x0, // 15 -> invalid
		0x0, 0x0, 0x0, 0x0, // 0
		0x0, 0x0, (byte)0x20, 0x41, // 10.0, 0 1000001|0 0100000|00000000|00000000
		0x0, 0x0, (byte)0x20, (byte)0xc1, // -10.0, 1 1000001|0 0100000|00000000|00000000
		0x0, 0x0, (byte)0x80, 0x3f, // 1.0, 0 0111111|1 0000000|00000000|00000000
	};
	
	public static final byte[] malformed_missing_data = {
		0x62, 0x6f, 0x78, 0x32, 0x66, 0x0, // "box2f\0"
		0x10, 0x0, 0x0, 0x0, // 16
		0x0, 0x0, 0x0, 0x0, // 0
		0x0, 0x0, (byte)0x20, 0x41, // 10.0, 0 1000001|0 0100000|00000000|00000000
		0x0, 0x0, (byte)0x20, (byte)0xc1, // -10.0, 1 1000001|0 0100000|00000000|00000000
		0x0, 0x0, (byte)0x80, // missing one byte
	};

	public void testNormal() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("box2f", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.box2f, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(16, length);

		Attribute attribute = AttributeBuilder.build("custom", at, length, stream);
		assertEquals("custom", attribute.getName());
		byte[] byteArray = attribute.getRawData();
		float xMin = AttributeReader.Box2f.get(byteArray, Component.XMin);
		float yMin = AttributeReader.Box2f.get(byteArray, Component.YMin);
		assertEquals(0.f, xMin);
		assertEquals(10.f, yMin);
		float xMax = AttributeReader.Box2f.get(byteArray, Component.XMax);
		float yMax = AttributeReader.Box2f.get(byteArray, Component.YMax);
		assertEquals(-10.f, xMax);
		assertEquals(1.f, yMax);
	}

	public void testInvalidLength() throws Exception {
		InputStream stream = new ByteArrayInputStream(malformed_invalid_length);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("box2f", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.box2f, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(15, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException mhe) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	public void testMissingData() throws Exception {
		InputStream stream = new ByteArrayInputStream(malformed_missing_data);

		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("box2f", type);

		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.box2f, at);

		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(16, length);

		boolean thrown = false;
		try {
			AttributeBuilder.build("custom", at, length, stream);
		} catch(MalformedHeaderException eoie) {
			thrown = true;
		}
		assertTrue(thrown);
	}
}
