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
 * Compression.java
 * @since 2009-04-21
 * 
 * $Id$
 */
package com.aqy.openexr4j.header.attr;


/**
 * Compression.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-21
 * @version $Id$
 *
 */
public class Compression implements AttributeReader {
	
	/**
	 * Gets the compression method given a byte array.
	 * 
	 * @param byteArray the byte array
	 * @return the compression method
	 * 
	 * @see {@link com.aqy.openexr4j.header.attr.Compression.CompressionMethod}
	 * 
	 * */
	public CompressionMethod get(byte[] byteArray) {
		byte index = byteArray[0];
		return CompressionMethod.values()[index];
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(int length) {
		return length == 1;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	@Override
	public boolean accept(byte[] byteArray) {
		return byteArray[0] < CompressionMethod.values().length;
	}

	/**
	 * Compression methods available for OpenEXR files.
	 * 
	 * */
	public enum CompressionMethod {
		/**
		 * 
		 * */
		NO_COMPRESSION,
		
		/**
		 * 
		 * */
		RLE_COMPRESSION,
		
		/**
		 * 
		 * */
		ZIPS_COMPRESSION,
		
		/**
		 * 
		 * */
		ZIP_COMPRESSION,
		
		/**
		 * 
		 * */
		PIZ_COMPRESSION,
		
		/**
		 * 
		 * */
		PXR24_COMPRESSION,
		
		/**
		 * 
		 * */
		B44_COMPRESSION,
		
		/**
		 * 
		 * */
		B44A_COMPRESSION,
	}
}
