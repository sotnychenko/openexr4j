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
 * LevelMode.java
 * @since 2009-04-18
 * 
 * $Id$
 */
package com.aqy.openexr4j.header;

/**
 * LevelMode.java
 *
 * @author alfred.qyang@gmail.com
 * @since 2009-04-18
 * @version $Id$
 *
 */
public enum LevelMode {
	/**
	 * The file contains only a single full-resolution level. 
	 * A tiled ONE_LEVEL file is equivalent to a scan-line-based 
	 * file; the only differences is that pixels are accessed 
	 * by tile rather than by scan line.
	 * 
	 * */
	ONE_LEVEL,
	/**
	 * The file contains multiple versions of the image. Each 
	 * successive level is half the resolution of the previous 
	 * level in both dimensions. The lowest-resolution level 
	 * contains only a single pixel. For example, if the first 
	 * level, with full resolution, contains 16x8 pixels, then 
	 * the file contains four more levels with 8x4, 4x2, 2x1, 
	 * and 1x1 respectively.
	 * 
	 * */
	MIPMAP_LEVELS,
	/**
	 * Like MIPMAP_LEVELS, but with more levels. The levels include 
	 * all combinations of reducing the resolution of the first 
	 * level by powers of two independently in both dimensions. 
	 * For example, if the first level contains 4x4 pixels, then 
	 * the file contains eight more levels, with the following 
	 * resolution:
	 * 
	 *          2x4   1x4
	 *    4x2   2x2   1x2
	 *    4x1   2x1   1x1
	 * 
	 * */
	RIPMAP_LEVELS
}
