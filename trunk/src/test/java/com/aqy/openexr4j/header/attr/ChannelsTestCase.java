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
 * ChannelsTestCase.java
 * @since 2009-04-27
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import com.aqy.openexr4j.header.MalformedHeaderException;
import com.aqy.openexr4j.header.io.ReaderUtils;

/**
 * ChannelsTestCase.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-27
 * @version $Id$
 *
 */
public class ChannelsTestCase extends TestCase {

	public static final byte[] data = {
		0x63, 0x68, 0x61, 0x6e, 0x6e, 0x65, 0x6c, 0x73, 0x0, // "channels\0"
		0x63, 0x68, 0x6c, 0x69, 0x73, 0x74, 0x0, // "chlist\0"
		0x25, 0x0, 0x0, 0x0, //37
		0x47, 0x0, // "G\0"
		0x1, 0x0, 0x0, 0x0, // HALF
		0x0, // 0
		0x0, 0x0, 0x0, // {0, 0, 0}
		0x1, 0x0, 0x0, 0x0, // 1
		0x1, 0x0, 0x0, 0x0, // 1
		0x5a, 0x0, // "Z\0"
		0x2, 0x0, 0x0, 0x0, // FLOAT
		0x0, // 0
		0x0, 0x0, 0x0, // {0, 0, 0}
		0x1, 0x0, 0x0, 0x0, // 1
		0x1, 0x0, 0x0, 0x0, // 1
		0x0, // null-terminated
	};
	
	public static final byte[] malformed = {
		0x63, 0x68, 0x61, 0x6e, 0x6e, 0x65, 0x6c, 0x73, 0x0, // "channels\0"
		0x63, 0x68, 0x6c, 0x69, 0x73, 0x74, 0x0, // "chlist\0"
		0x24, 0x0, 0x0, 0x0, //36 -> length not includes null terminator
		0x47, 0x0, // "G\0"
		0x1, 0x0, 0x0, 0x0, // HALF
		0x0, // 0
		0x0, 0x0, 0x0, // {0, 0, 0}
		0x1, 0x0, 0x0, 0x0, // 1
		0x1, 0x0, 0x0, 0x0, // 1
		0x5a, 0x0, // "Z\0"
		0x2, 0x0, 0x0, 0x0, // FLOAT
		0x0, // 0
		0x0, 0x0, 0x0, // {0, 0, 0}
		0x1, 0x0, 0x0, 0x0, // 1
		0x1, 0x0, 0x0, 0x0, // 1
		0x0, // null-terminated
	};
	
	public void testChannels() throws Exception {
		InputStream stream = new ByteArrayInputStream(data);
		
		String name = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("channels", name);
		
		RequiredAttribute ra = RequiredAttribute.valueOf(name);
		assertEquals(RequiredAttribute.channels, ra);
		
		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("chlist", type);
		
		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.chlist, at);
		
		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(37, length);
		
		Attribute attribute = ra.build(at, length, stream);
		assertEquals("channels", attribute.getName());
		byte[] byteArray = attribute.getRawData();
		List<ImageChannel> list = AttributeReader.Chlist.get(byteArray);
		assertNotNull(list);
		assertEquals(2, list.size());
	}
	
	public void testMalformed() throws Exception {
		InputStream stream = new ByteArrayInputStream(malformed);
		
		String name = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("channels", name);
		
		RequiredAttribute ra = RequiredAttribute.valueOf(name);
		assertEquals(RequiredAttribute.channels, ra);
		
		String type = ReaderUtils.readOpenEXRStringWithinMaxAndMinLenghth(stream);
		assertEquals("chlist", type);
		
		AttributeType at = AttributeType.safeValueOf(type);
		assertEquals(AttributeType.chlist, at);
		
		int length = ReaderUtils.streamReader.readInteger(stream);
		assertEquals(36, length);
		
		boolean thrown = false;
		try {
			ra.build(at, length, stream);
		} catch(MalformedHeaderException mhe) {
			System.out.println(mhe.getMessage());
			thrown = true;
		}
		assertTrue(thrown);
	}
}
