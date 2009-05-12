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
 * HeaderUtils.java
 * @since 2009-04-20
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.io;

import static com.aqy.openexr4j.V2Reader.ATTRIBUTE_NAME_MAX_LENGTH;
import static com.aqy.openexr4j.V2Reader.ATTRIBUTE_NAME_MIN_LENGTH;

import java.io.IOException;
import java.io.InputStream;

import com.aqy.openexr4j.header.MalformedHeaderException;


/**
 * HeaderUtils.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-20
 * @version $Id$
 *
 */
public class ReaderUtils {

	/**
	 * Stream reader to read from the provided input stream.
	 * 
	 * */
	public final static StreamReader streamReader = new StreamReader();
	
	/**
	 * Byte array reade to read from a provided byte array.
	 * 
	 * */
	public final static ByteArrayReader byteArrayReader = new ByteArrayReader();
	
	/**
	 * A helper method to read a OpenEXR string from an input stream. The maximum 
	 * length of an OpenEXR string is defined at OpenEXRHeader.ATTRIBUTE_NAME_MAX_LENGTH.
	 * 
	 *  @see {@link com.aqy.openexr4j.V2Reader}
	 * 
	 * */
	public static String readOpenEXRStringWithinMaxLenghth(InputStream stream) 
	throws MalformedHeaderException, IOException {
		try {
			return streamReader.readString(stream, ATTRIBUTE_NAME_MAX_LENGTH);
		} catch(StringTooLongException stle) {
			throw new MalformedHeaderException("String too long.", stle);
		} catch(EndOfInputException eoie) {
			throw new MalformedHeaderException("Unexpected end of input.", eoie);
		}
	}
	
	/**
	 * A helper method to read a OpenEXR string from an input stream. The maximum 
	 * length of an OpenEXR string is defined at OpenEXRHeader.ATTRIBUTE_NAME_MAX_LENGTH.
	 * The minimum lenght is defined at OpenEXRHeader.ATTRIBUTE_NAME_MIN_LENGTH.
	 * 
	 *  @see {@link com.aqy.openexr4j.V2Reader}
	 * 
	 * */
	public static String readOpenEXRStringWithinMaxAndMinLenghth(InputStream stream) 
	throws MalformedHeaderException, IOException {
		try {
			return streamReader.readString(stream, ATTRIBUTE_NAME_MIN_LENGTH, ATTRIBUTE_NAME_MAX_LENGTH);
		} catch(StringTooLongException stle) {
			// string too long
			throw new MalformedHeaderException("String too long.", stle);
		} catch(StringTooShortException stse) {
			// string too short
			throw new MalformedHeaderException("String too short.", stse);
		} catch(EndOfInputException eoie) {
			// stream too short
			throw new MalformedHeaderException("Unexpected end of input.", eoie);
		}
	}
	
	/**
	 * A helper class to perform a repetitive read until a null is read. 
	 * 
	 * */
	public static abstract class ReadTillNull {
		
		private final InputStream stream;
		
		public ReadTillNull(InputStream stream) {
			this.stream = stream;
		}
		/**
		 * Starts to perform the repetitive read.
		 * 
		 * */
		public void startRead() throws MalformedHeaderException {
			try {
				String s = readOpenEXRStringWithinMaxLenghth(stream);
				while(s.length() > 0) {
					onRead(s);
					s = readOpenEXRStringWithinMaxLenghth(stream);
				}
			} catch(MalformedHeaderException mhe) {
				throw mhe;
			} catch(Exception e) {
				throw new MalformedHeaderException(String.format("Error reading header."), e);
			}
		}

		/**
		 * user specified process on the string read
		 * 
		 * @param s the string read
		 * 
		 * */
		public abstract void onRead(String s) throws Exception;
	}
}
