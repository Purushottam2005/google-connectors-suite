/**
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 **/
        
/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.mule.module.gmail;

import org.apache.commons.lang.StringUtils;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.oauth.OAuth2;
import org.mule.api.annotations.oauth.OAuthAccessToken;
import org.mule.api.annotations.oauth.OAuthAccessTokenIdentifier;
import org.mule.api.annotations.oauth.OAuthAuthorizationParameter;
import org.mule.api.annotations.oauth.OAuthConsumerKey;
import org.mule.api.annotations.oauth.OAuthConsumerSecret;
import org.mule.api.annotations.oauth.OAuthScope;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.google.AccessType;
import org.mule.modules.google.ForcePrompt;
import org.mule.modules.google.IdentifierPolicy;
import org.mule.modules.google.oauth.invalidation.OAuthTokenExpiredException;

import com.google.code.javax.mail.AuthenticationFailedException;
import com.google.code.javax.mail.MessagingException;
import com.google.code.javax.mail.Store;
import com.google.code.oauth.OAuth2Authenticator;

/**
 * Gmail cloud connector.
 * This connector covers the standard IMAP protocol plus Google's extensions for Gmail.
 * This implementation uses OAuth2 for authentication
 *
 * @author mariano.gonzalez@mulesoft.com
 */
@Connector(name="gmail", schemaVersion="1.0", friendlyName="GMail Connector (OAuth2)", minMuleVersion="3.4", configElementName="config-with-oauth")
@OAuth2(
		authorizationUrl = "https://accounts.google.com/o/oauth2/auth",
		accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
		accessTokenRegex="\"access_token\"[ ]*:[ ]*\"([^\\\"]*)\"",
		expirationRegex="\"expires_in\"[ ]*:[ ]*([\\d]*)",
		refreshTokenRegex="\"refresh_token\"[ ]*:[ ]*\"([^\\\"]*)\"",
		authorizationParameters={
				@OAuthAuthorizationParameter(name="access_type", defaultValue="online", type=AccessType.class, description="Indicates if your application needs to access a Google API when the user is not present at the browser. " + 
											" Use offline to get a refresh token and use that when the user is not at the browser. Default is online", optional=true),
				@OAuthAuthorizationParameter(name="force_prompt", defaultValue="auto", type=ForcePrompt.class, description="Indicates if google should remember that an app has been authorized or if each should ask authorization every time. " + 
											" Use force to request authorization every time or auto to only do it the first time. Default is auto", optional=true)
		}
)
public class GmailConnector extends BaseGmailConnector {
	
	private static Boolean providerInited = false;

	/**
     * The OAuth2 consumer key 
     */
    @Configurable
    @OAuthConsumerKey
    private String consumerKey;

    /**
     * The OAuth2 consumer secret 
     */
    @Configurable
    @OAuthConsumerSecret
    private String consumerSecret;
    
    /**
     * Application name registered on Google API console
     */
    @Configurable
    @Optional
    @Default("Mule-GmailConnector/1.0")
    private String applicationName;
    
    /**
     * The OAuth scopes you want to request
     */
    @OAuthScope
    @Configurable
    @Optional
    @Default(USER_PROFILE_SCOPE + " https://mail.google.com/")
    private String scope;
    
    /**
     * This policy represents which id we want to use to represent each google account.
     * 
     * PROFILE means that we want the google profile id. That means, the user's primary key in google's DB.
     * This is a long number represented as a string.
     * 
     * EMAIL means you want to use the account's email address
     */
    @Configurable
    @Optional
    @Default("EMAIL")
    private IdentifierPolicy identifierPolicy = IdentifierPolicy.EMAIL;
    
    @OAuthAccessToken
    private String accessToken;
    
    @Start
    public void init() {
    	synchronized (providerInited) {
    		if (!providerInited) {
    			OAuth2Authenticator.initialize();
    			providerInited = true;
    		}
		}
    }
    
	@Override
	protected Store getStore(String username, String password) throws MessagingException {
		String token = this.getAccessToken();
		
		if (StringUtils.isEmpty(token)) {
			throw new IllegalStateException("Cannot connect without an access token");
		}
		
		try {
			return OAuth2Authenticator.connectToImap(username, token);
		} catch (AuthenticationFailedException e) {
			throw new OAuthTokenExpiredException("Authentication failed", e);
		} catch (MessagingException e) {
			throw new RuntimeException("There was an unexpected error connecting to the imap store using OAuth2", e);
		}
	}
	
	@OAuthAccessTokenIdentifier
	public String getAccessTokenId() {
		return this.identifierPolicy.getId(this);
	}
	
    @Override
    public String getAccessToken() {
    	return this.accessToken;
    }

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public IdentifierPolicy getIdentifierPolicy() {
		return identifierPolicy;
	}

	public void setIdentifierPolicy(IdentifierPolicy identifierPolicy) {
		this.identifierPolicy = identifierPolicy;
	}
	
	
}
