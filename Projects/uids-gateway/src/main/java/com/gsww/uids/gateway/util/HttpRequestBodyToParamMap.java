/*
 * $Id: HttpRequestBodyToParamMap.java 12661 2008-09-16 17:43:43Z dirk.olmes $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.gsww.uids.gateway.util;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageAwareTransformer;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;

public class HttpRequestBodyToParamMap extends AbstractMessageAwareTransformer
{
    
    public HttpRequestBodyToParamMap()
    {
        registerSourceType(Object.class);
        setReturnClass(Object.class);
    }

    public Object transform(MuleMessage message, String encoding) 
        throws TransformerException
    {
        HashMap paramMap = new HashMap();

        try
        {
            String httpMethod = (String) message.getProperty("http.method");
            String contentType = (String) message.getProperty("Content-Type");

            if (!("GET".equalsIgnoreCase(httpMethod) || ("POST".equalsIgnoreCase(httpMethod) 
                && "application/x-www-form-urlencoded".equalsIgnoreCase(contentType))))
            {
                throw new Exception("The HTTP method or content type is unsupported!");
            }

            String queryString = null;
            if ("GET".equalsIgnoreCase(httpMethod))
            {
                URI uri = new URI(message.getPayloadAsString(encoding));
                queryString = uri.getQuery();
            }
            else if ("POST".equalsIgnoreCase(httpMethod))
            {
            	queryString = new String(message.getPayloadAsBytes());
            }

            if (queryString != null && queryString.length() > 0)
            {
                String[] pairs = queryString.split("&");
                for (int x = 0; x < pairs.length; x++)
                {
                    String[] nameValue = pairs[x].split("=");
                    if (nameValue.length == 2)
                   {
                        paramMap.put(URLDecoder.decode(nameValue[0], encoding), URLDecoder.decode(
                            nameValue[1], encoding));
                   }else {
                	   paramMap.put(URLDecoder.decode(nameValue[0], encoding), URLDecoder.decode(
                              "", encoding));
                   }
                }
            }
        }
        catch (Exception e)
        {
            throw new TransformerException(this, e);
        }

        return paramMap;

    }

    public boolean isAcceptNull()
    {
        return false;
    }

}

