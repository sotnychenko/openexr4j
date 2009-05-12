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
 * DisplayWindowTestCase.java
 * @since 2009-04-25
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.aqy.openexr4j.Pixel;
import com.aqy.openexr4j.header.attr.Box2i.Component;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * DisplayWindowTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-25
 * @version $Id$
 *
 */
public class CustomizedAttributeTestCase extends TestCase {
	
	public static final byte[] data = {
		0x64, 0x69, 0x73, 0x70, 0x6c, 0x61, 0x79, 0x57, 0x69, 0x6e, 0x64, 0x6f, 0x77, 0x0, //"displayWindow\0"
		0x62, 0x6f, 0x78, 0x32, 0x69, 0x0, // "box2i\0"
		0x10, 0x0, 0x0, 0x0, // 16
		0x0, 0x0, 0x0, 0x0, // 0
		0x0, 0x0, 0x0, 0x0, // 0
		0x3, 0x0, 0x0, 0x0, // 3
		0x2, 0x0, 0x0, 0x0, // 2
	};

	/**
	 * @throws Exception
	 */
	public void testDisplayWindow() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);
		
		String name = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("displayWindow", name);
		
		RequiredAttribute ra = RequiredAttribute.valueOf(name);
		assertEquals(RequiredAttribute.displayWindow, ra);
		
		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("box2i", type);
		
		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.box2i, at);
		
		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(16, length);
		
		Attribute attribute = ra.build(at, length, stream);
		assertEquals("displayWindow", attribute.getName());
		byte[] byteArray = attribute.getRawData();
		int xMin = AttributeReader.Box2i.get(byteArray, Component.XMin);
		int yMin = AttributeReader.Box2i.get(byteArray, Component.YMin);
		Pixel upperLeft = new Pixel(xMin, yMin);
		assertEquals(0, upperLeft.x);
		assertEquals(0, upperLeft.y);
		int xMax = AttributeReader.Box2i.get(byteArray, Component.XMax);
		int yMax = AttributeReader.Box2i.get(byteArray, Component.YMax);
		Pixel lowerRight = new Pixel(xMax, yMax);
		assertEquals(3, lowerRight.x);
		assertEquals(2, lowerRight.y);
	}
}
