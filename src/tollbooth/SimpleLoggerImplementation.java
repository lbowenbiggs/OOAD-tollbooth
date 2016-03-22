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

public class SimpleLoggerImplementation implements SimpleLogger {

	@Override
	public void accept(LogMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public LogMessage getNextMessage() {
		// TODO Auto-generated method stub
		return new LogMessage("open: malfunction");
	}

}
