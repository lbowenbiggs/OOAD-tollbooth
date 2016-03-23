/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package tollbooth;

import tollbooth.gatecontroller.GateController;

/**
 * The TollGate contains everything about a tollgate in a tollbooth.
 * @version Feb 3, 2016
 */
public class TollGate
{
	private final GateController controller;
	private final SimpleLogger logger;
	
	private boolean willNotRespond;
	
	/**
	 * Constructor that takes the actual gate controller and the logger.
	 * @param controller the GateController object.
	 * @param logger the SimpleLogger object.
	 */
	public TollGate(GateController controller, SimpleLogger logger) {
		this.controller = controller;
		this.logger = logger;
		
		willNotRespond = false;
	}
	
	/**
	 * Open the gate.
	
	 * @throws TollboothException */
	public void open() throws TollboothException
	{
		int maxAttempts = 3;
		int numAttempts = 0;
		
		TollboothException lastFailureCause = new TollboothException("");
		
		if (willNotRespond)
		{
			logger.accept(new LogMessage("open: will not respond"));
			return;
		}
		
		while (!isOpen() && numAttempts < maxAttempts)
		{
			numAttempts++;
			try 
			{
				controller.open();
			} 
			catch (TollboothException e)
			{
				logger.accept(new LogMessage("open: malfunction", e));
				lastFailureCause = e;
			}
		}
		
		if (isOpen())
		{
			logger.accept(new LogMessage("open: successful"));
		}
		else 
		{
			willNotRespond = true;
			logger.accept(new LogMessage("open: unrecoverable malfunction", lastFailureCause));
		}
	}
	
	/**
	 * Close the gate
	
	 * @throws TollboothException */
	public void close() throws TollboothException
	{
		int maxAttempts = 3;
		int numAttempts = 0;
		
		TollboothException lastFailureCause = new TollboothException("");
		
		if (willNotRespond)
		{
			logger.accept(new LogMessage("close: will not respond"));
			return;
		}
		
		while (isOpen() && numAttempts < maxAttempts)
		{
			numAttempts++;
			try 
			{
				controller.close();
			} 
			catch (TollboothException e)
			{
				logger.accept(new LogMessage("close: malfunction", e));
				lastFailureCause = e;
			}
		}
		
		if (!isOpen())
		{
			logger.accept(new LogMessage("close: successful"));
		}
		else 
		{
			willNotRespond = true;
			logger.accept(new LogMessage("close: unrecoverable malfunction", lastFailureCause));
		}
	}
	
	/**
	 * Reset the gate to the state it was in when created with the exception of the
	 * statistics.
	
	 * @throws TollboothException */
	public void reset() throws TollboothException
	{
		// To be completed
	}
	
	/**
	 * @return true if the gate is open
	 * @throws TollboothException 
	 */
	public boolean isOpen() throws TollboothException
	{
			return controller.isOpen();
	}
	
	/**
	 * @return the number of times that the gate has been opened (that is, the
	 *  open method has successfully been executed) since the object was created.
	 */
	public int getNumberOfOpens()
	{
		// To be completed
		return -1;
	}
	
	/**
	 * @return the number of times that the gate has been closed (that is, the
	 *  close method has successfully been executed) since the object was created.
	 */
	public int getNumberOfCloses()
	{
		// To be completed
		return -1;
	}
	
	/**
	 * Method makeNonResponsive.
	 * Sets willNotRespond to true
	 */
	public void makeNonResponsive()
	{
		willNotRespond = true;
	}
	
	public boolean isResponsive()
	{
		return !willNotRespond;
	}
}
