/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.google.code.javax.mail.event;

import com.google.code.javax.mail.Address;
import com.google.code.javax.mail.Message;
import com.google.code.javax.mail.Transport;

/**
 * This class models Transport events.
 *
 * @author John Mani
 * @author Max Spivak
 * 
 * @see javax.mail.Transport
 * @see javax.mail.event.TransportListener
 */

public class TransportEvent extends MailEvent {

    /**
     * Message has been	successfully delivered to all recipients by the
     * transport firing this event. validSent[] contains all the addresses
     * this transport sent to successfully. validUnsent[] and invalid[] 
     * should be null,
     */
    public static final int MESSAGE_DELIVERED	  = 1;

    /**
     * Message was not sent for some reason. validSent[] should be null. 
     * validUnsent[] may have addresses that are valid (but the message
     * wasn't sent to them). invalid[] should likely contain invalid addresses.
     */
    public static final int MESSAGE_NOT_DELIVERED = 2;

    /**
     * Message was successfully sent to some recipients but not to all. 
     * validSent[] holds addresses of recipients to whom the message was sent.
     * validUnsent[] holds valid addresses to which the message was not sent.
     * invalid[] holds invalid addresses, if any.
     */
    public static final int MESSAGE_PARTIALLY_DELIVERED = 3;


    /**
     * The event type.
     *
     * @serial
     */
    protected int type;

    transient protected Address[] validSent;
    transient protected Address[] validUnsent;
    transient protected Address[] invalid;
    transient protected Message msg;

    private static final long serialVersionUID = -4729852364684273073L;

    /**
     * Constructor.
     * @param transport  The Transport object
     */
    public TransportEvent(Transport transport, int type, Address[] validSent,
			  Address[] validUnsent, Address[] invalid,
			  Message msg) {
	super(transport);
	this.type = type;
	this.validSent = validSent;
	this.validUnsent = validUnsent;
	this.invalid = invalid;
	this.msg = msg;
    }

    /**
     * Return the type of this event.
     * @return  type
     */
    public int getType() {
	return type;
    }

    /**
     * Return the addresses to which this message was sent succesfully.
     * @return Addresses to which the message was sent successfully or null
     */
    public Address[] getValidSentAddresses() {
	return validSent;
    }

    /**
     * Return the addresses that are valid but to which this message 
     * was not sent.
     * @return Addresses that are valid but to which the message was 
     *         not sent successfully or null
     */
    public Address[] getValidUnsentAddresses() {
	return validUnsent;
    }

    /**
     * Return the addresses to which this message could not be sent.
     * @return Addresses to which the message sending failed or null
     */
    public Address[] getInvalidAddresses() {
	return invalid;
    }

    /**
     * Get the Message object associated with this Transport Event.
     *   
     * @return          the Message object
     * @since		JavaMail 1.2
     */  
    public Message getMessage() {
        return msg;
    }

    /**
     * Invokes the appropriate TransportListener method.
     */
    public void dispatch(Object listener) {
	if (type == MESSAGE_DELIVERED)	
	    ((TransportListener)listener).messageDelivered(this);
	else if (type == MESSAGE_NOT_DELIVERED)
	    ((TransportListener)listener).messageNotDelivered(this);
	else // MESSAGE_PARTIALLY_DELIVERED
	    ((TransportListener)listener).messagePartiallyDelivered(this);
    }
}
