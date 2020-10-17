/**
 * This package contains Security-related classes such as JWT authentication,
 * JWT token provider and more..
 * JWT (JSON Web Tokens) are used in authorizations in our web application.
 * The user provides his credentials to the server and the server responds back
 * the client in return with an authenticated JWT (if his credentials are correct).
 * JWTProvider sets up those tokens and the JWTAuthenticationFilter intercepts the
 * clients request for data.
 */
package com.example.springreddit.security;