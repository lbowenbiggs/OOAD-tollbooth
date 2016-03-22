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

package tollbooth.gatecontroller;

import tollbooth.TollboothException;

/**
 * Description
 * @version Feb 15, 2016
 */
public class TestGateController implements GateController
{
	boolean isOpen;
	int failNextNOpens;
	
	/**
	 * Constructor for the test gate controller.
	 */
	public TestGateController()
	{
		isOpen = false;
		failNextNOpens = 0;
	}
	
	/*
	 * @see tollbooth.gatecontroller.GateController#open()
	 */
	@Override
	public void open() throws TollboothException
	{
		if (failNextNOpens > 0)
		{
			failNextNOpens -= 1;
			throw new TollboothException("open: malfunction");
		}
		else
		{
			isOpen = true;
		}
	}

	/*
	 * @see tollbooth.gatecontroller.GateController#close()
	 */
	@Override
	public void close() throws TollboothException
	{
		// TODO Auto-generated method stub

	}

	/*
	 * @see tollbooth.gatecontroller.GateController#reset()
	 */
	@Override
	public void reset() throws TollboothException
	{
		// TODO Auto-generated method stub

	}

	/*
	 * @see tollbooth.gatecontroller.GateController#isOpen()
	 */
	@Override
	public boolean isOpen() throws TollboothException
	{
		return isOpen;
	}
	
	/**
	 * Method makeNextNOpensFail sets the number of failures that will
	 * occur before a successful open() 
	 * 
	 * @param numberFails the number of times open() should fail 
	 */
	public void makeNextNOpensFail(int numberFails)
	{
		failNextNOpens = numberFails;
	}
}
