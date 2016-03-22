/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Leo Bowen-Biggs
 *******************************************************************************/

package tollbooth;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements the SimpleLogger Interface as a wrapper for a queue
 */
public class SimpleLoggerImplementation implements SimpleLogger {
	private Queue<LogMessage> messageQueue = new LinkedList<LogMessage>();
	
	@Override
	public void accept(LogMessage message) {
		messageQueue.add(message);
	}

	@Override
	public LogMessage getNextMessage() {
		return messageQueue.remove();
	}

}
